package com.kw.api.domain.bundle.service

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.domain.bundle.dto.request.*
import com.kw.api.domain.bundle.dto.response.BundleGetResponse
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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

    fun createBundle(request: BundleCreateRequest): BundleGetResponse {
        val tags: List<Tag> = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        val bundle = bundleRepository.save(request.toEntity())
        bundle.updateBundleTags(tags)
        return getBundle(bundle.id!!)
    }

    fun searchBundles(
        searchCondition: BundleSearchCondition,
        pageCondition: PageCondition
    ): PageResponse<BundlesGetResponse> {
        val pageable: Pageable = PageRequest.of(pageCondition.page.minus(1), pageCondition.size)
        val bundles: List<BundlesGetResponse> = bundleRepository.findAll(searchCondition, pageable)
            .map { BundlesGetResponse.from(it) }
        return PageResponse.from(
            PageableExecutionUtils.getPage(
                bundles, pageable, LongSupplier { bundleRepository.count(searchCondition) }
            )
        )
    }

    fun getMyBundles(getCondition: BundleGetCondition): List<BundlesGetResponse> {
        val bundles = bundleRepository.findAllByMemberId(1L, getCondition) //TODO: 임시 memberId, 인증 기능 추가 후 수정
        return bundles.map { BundlesGetResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getBundle(id: Long): BundleGetResponse {
        val bundle = bundleRepository.findDetailById(id)
            ?: throw IllegalArgumentException("존재하지 않는 꾸러미입니다.")
        return BundleGetResponse.from(bundle)
    }

    fun updateBundle(id: Long, request: BundleUpdateRequest) {
        val bundle = bundleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 꾸러미입니다.") }
        bundle.updateNameAndShareType(request.name, Bundle.ShareType.from(request.shareType))

        val foundTags = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        bundle.updateBundleTags(foundTags)
    }

    fun deleteBundle(id: Long) {
        val bundle = bundleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 꾸러미입니다.") }
        bundleRepository.delete(bundle)
    }

    fun addQuestion(id: Long, request: BundleQuestionAddRequest) {
        val bundle = bundleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 꾸러미입니다.") }
        val questions = getExistQuestions(request.questionIds)
        val copiedQuestions = questions.map(Question::copy)
        bundle.addQuestions(copiedQuestions)
        //TODO: update questionOrderList
    }

    fun removeQuestion(id: Long, request: BundleQuestionRemoveRequest) {
        val bundle = bundleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 꾸러미입니다.") }
        val questions = getExistQuestions(request.questionIds)
        bundle.removeQuestions(questions)
        //TODO: update questionOrderList
    }

//    fun updateQuestionOrderList(id: Long, request: BundleQuestionOrderListUpdateRequest) {}

    private fun getExistTags(tagIds: List<Long>): List<Tag> {
        val tags = tagRepository.findAllById(tagIds)
        if (tags.size != tagIds.size) {
            throw IllegalArgumentException("존재하지 않는 태그가 포함되어 있습니다.")
        }
        return tags
    }

    private fun getExistQuestions(questionIds: List<Long>): List<Question> {
        val questions = questionRepository.findAllById(questionIds)
        if (questions.size != questionIds.size) {
            throw IllegalArgumentException("존재하지 않는 질문이 포함되어 있습니다.")
        }
        return questions
    }

}
