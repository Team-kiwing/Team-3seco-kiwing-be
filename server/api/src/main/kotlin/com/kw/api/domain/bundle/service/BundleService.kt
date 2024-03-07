package com.kw.api.domain.bundle.service

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.bundle.dto.request.*
import com.kw.api.domain.bundle.dto.response.BundleDetailResponse
import com.kw.api.domain.bundle.dto.response.BundleResponse
import com.kw.api.domain.member.dto.request.BundleOrderUpdateRequest
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.BundleTag
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.LongSupplier

@Service
@Transactional
class BundleService(
    private val bundleRepository: BundleRepository,
    private val tagRepository: TagRepository,
    private val questionRepository: QuestionRepository,
) {

    fun createBundle(request: BundleCreateRequest, member: Member): BundleDetailResponse {
        val tags = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        val bundle = request.toEntity(member)
        bundle.updateBundleTags(tags.map { BundleTag(bundle, it) })
        member.updateBundleOrder((member.bundleOrder + " " + bundle.id).trim())
        return getBundle(bundleRepository.save(bundle).id!!, false, member)
    }

    @Transactional(readOnly = true)
    fun searchBundles(
        searchCondition: BundleSearchCondition,
        pageCondition: PageCondition
    ): PageResponse<BundleResponse> {
        val pageable = PageRequest.of(pageCondition.page.minus(1), pageCondition.size)
        val bundles = bundleRepository.findAll(searchCondition, pageable)
            .map { BundleResponse.from(it) }
        return PageResponse.from(
            PageableExecutionUtils.getPage(
                bundles, pageable, LongSupplier { bundleRepository.count(searchCondition) }
            )
        )
    }

    @Transactional(readOnly = true)
    fun getMyBundles(getCondition: BundleGetCondition, member: Member): List<BundleResponse> {
        val bundles = bundleRepository.findAllByMemberId(member.id!!, getCondition)

        if (getCondition.sortingType == BundleGetCondition.SortingType.CUSTOM) {
            val bundleOrder = parseOrderStringToOrderList(member.bundleOrder)
            val sortedBundles = bundles.sortedBy { bundleOrder.indexOf(it.id) }
            return sortedBundles.map { BundleResponse.from(it) }
        }

        return bundles.map { BundleResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getBundle(id: Long, showOnlyMyQuestions: Boolean? = false, member: Member): BundleDetailResponse {
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)
        if (bundle.shareType == Bundle.ShareType.PRIVATE && bundle.member.id != member.id) {
            return BundleDetailResponse(
                id = bundle.id!!,
                shareType = bundle.shareType.name,
                originId = bundle.originId,
                writerId = bundle.member.id!!,
            )
        }

        val questions = questionRepository.findAllWithTagsByBundleId(
            id,
            showOnlyMyQuestions,
            member.id
        )
        val questionOrder = parseOrderStringToOrderList(bundle.questionOrder)
        val sortedQuestions = questions.sortedBy { questionOrder.indexOf(it.id) }

        return BundleDetailResponse.from(bundle, sortedQuestions)
    }

    fun updateBundle(id: Long, request: BundleUpdateRequest, member: Member): BundleResponse {
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)
        if (bundle.member.id != member.id) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        request.name?.let { bundle.updateName(request.name) }
        request.shareType?.let { bundle.updateShareType(request.shareType) }
        request.tagIds?.let { it ->
            val tags = getExistTags(it)
            bundle.updateBundleTags(tags.map { BundleTag(bundle, it) })
        }

        return BundleResponse.from(bundle)
    }

    fun updateBundleOrder(member: Member, request: BundleOrderUpdateRequest) {
        member.updateBundleOrder(request.bundleIds.joinToString(" "))
    }

    fun deleteBundle(id: Long, member: Member) {
        val bundle = getExistBundle(id)
        if (bundle.member.id != member.id) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }
        bundle.originId?.let { bundleRepository.decreaseScrapeCount(it) }

        val questions = questionRepository.findAllByBundleId(id)
        questionRepository.decreaseShareCountByIdIn(questions.mapNotNull { it.originId })

        bundleRepository.delete(bundle)
    }

    fun scrapeBundle(id: Long, member: Member) {
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)
        if (bundle.shareType == Bundle.ShareType.PRIVATE) {
            throw ApiException(ApiErrorCode.FORBIDDEN_BUNDLE)
        }
        bundleRepository.increaseScrapeCount(id)

        val questions = questionRepository.findAllWithTagsByBundleId(id)
        questionRepository.increaseShareCountByIdIn(questions.map { it.id!! })

        bundleRepository.save(bundle.copy(questions, member))
    }

    fun updateQuestionOrder(id: Long, request: BundleQuestionOrderUpdateRequest, member: Member) {
        val bundle = getExistBundle(id)
        if (bundle.member.id != member.id) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        bundle.updateQuestionOrder(request.questionOrder.joinToString(" "))
    }

    fun addQuestion(request: BundleQuestionAddRequest, member: Member) {
        val bundles = bundleRepository.findAllByMemberIdAndIdIn(member.id!!, request.bundleIds)
        if (bundles.size != request.bundleIds.size) {
            throw ApiException(ApiErrorCode.INCLUDE_INVALID_BUNDLE)
        }

        bundles.forEach { bundle ->
            val questionCount = questionRepository.countAllByBundleId(bundle.id!!)
            if (questionCount + request.questionIds.size >= 100) {
                throw ApiException(ApiErrorCode.OVER_QUESTION_LIMIT)
            }

            val questions = questionRepository.findAllWithTagsByIdIn(request.questionIds)
            questionRepository.increaseShareCountByIdIn(questions.map { it.id!! })

            val copiedAndSavedQuestions = questions
                .map { questionRepository.save(it.copy(bundle, member)) }
            bundle.updateQuestionOrder((bundle.questionOrder + " " + copiedAndSavedQuestions.joinToString(" ") { it.id.toString() }).trim())
        }
    }

    fun removeQuestion(id: Long, request: BundleQuestionRemoveRequest, member: Member) {
        val bundle = getExistBundle(id)
        if (bundle.member.id != member.id) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        val questions = getExistQuestions(request.questionIds)
        questionRepository.decreaseShareCountByIdIn(questions.mapNotNull { it.originId })

        bundle.removeQuestions(questions)
    }

    private fun getExistBundle(id: Long): Bundle {
        return bundleRepository.findById(id)
            .orElseThrow { ApiException(ApiErrorCode.NOT_FOUND_BUNDLE) }
    }

    private fun getExistTags(tagIds: List<Long>): List<Tag> {
        val tags = tagRepository.findAllById(tagIds)
        if (tags.size != tagIds.size) {
            throw ApiException(ApiErrorCode.INCLUDE_NOT_FOUND_TAG)
        }
        return tags
    }

    private fun getExistQuestions(questionIds: List<Long>): List<Question> {
        val questions = questionRepository.findAllById(questionIds)
        if (questions.size != questionIds.size) {
            throw ApiException(ApiErrorCode.INCLUDE_NOT_FOUND_QUESTION)
        }
        return questions
    }

    private fun parseOrderStringToOrderList(orderString: String): List<Long> {
        return if (orderString.isNotBlank()) {
            orderString.split(" ").map { it.toLong() }
        } else {
            emptyList()
        }
    }
}
