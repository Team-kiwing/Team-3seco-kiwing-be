package com.kw.api.domain.bundle.service

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.bundle.dto.request.BundleCreateRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionAddRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionRemoveRequest
import com.kw.api.domain.bundle.dto.request.BundleUpdateRequest
import com.kw.api.domain.bundle.dto.response.BundleDetailResponse
import com.kw.api.domain.bundle.dto.response.BundleResponse
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.BundleTag
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.kw.data.domain.bundle.repository.BundleRepository
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

    fun createBundle(request: BundleCreateRequest): BundleDetailResponse {
        val tags = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        val bundle = request.toEntity()
        bundle.updateBundleTags(tags.map { BundleTag(bundle, it) })
        return getBundle(bundleRepository.save(bundle).id!!)
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
    fun getMyBundles(getCondition: BundleGetCondition): List<BundleResponse> {
        val bundles = bundleRepository.findAllByMemberId(1L, getCondition) //TODO: 임시 memberId, 인증 기능 추가 후 수정
        return bundles.map { BundleResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getBundle(id: Long, showOnlyMyQuestion: Boolean? = null, memberId: Long? = null): BundleDetailResponse {
        val bundle =
            bundleRepository.findDetailById(id, showOnlyMyQuestion, memberId) //TODO: 임시 memberId, 인증 기능 추가 후 수정
                ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)
        return BundleDetailResponse.from(bundle)
    }

    fun updateBundle(id: Long, request: BundleUpdateRequest): BundleResponse {
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)

        request.name?.let { bundle.updateName(request.name) }
        request.shareType?.let { bundle.updateShareType(Bundle.ShareType.from(request.shareType)) }
        request.tagIds?.let { it ->
            val tags = getExistTags(it)
            bundle.updateBundleTags(tags.map { BundleTag(bundle, it) })
        }

        return BundleResponse.from(bundle)
    }

    fun deleteBundle(id: Long) {
        val bundle = getExistBundle(id)
        bundleRepository.delete(bundle)
    }

    fun scrapeBundle(id: Long) {
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw ApiException(ApiErrorCode.NOT_FOUND_BUNDLE)
        if (bundle.shareType == Bundle.ShareType.PRIVATE) {
            throw ApiException(ApiErrorCode.FORBIDDEN_BUNDLE)
        }

        val questions = questionRepository.findAllWithTagsByBundleId(id)

        bundleRepository.save(bundle.copy(questions))
    }

    fun addQuestion(id: Long, request: BundleQuestionAddRequest) {
        val bundle = getExistBundle(id)
        val questions = getExistQuestions(request.questionIds)
        val copiedQuestions = questions
            .filter { it.answerShareType == Question.AnswerShareType.PUBLIC }
            .map { it.copy(bundle) }
        bundle.addQuestions(copiedQuestions)
    }

    fun removeQuestion(id: Long, request: BundleQuestionRemoveRequest) {
        val bundle = getExistBundle(id)
        val questions = getExistQuestions(request.questionIds)
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

}
