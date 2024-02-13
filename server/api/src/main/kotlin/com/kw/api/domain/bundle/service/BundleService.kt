package com.kw.api.domain.bundle.service

import com.kw.api.domain.bundle.dto.request.BundleCreateRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionAddRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionRemoveRequest
import com.kw.api.domain.bundle.dto.request.BundleUpdateRequest
import com.kw.api.domain.bundle.dto.response.BundleGetResponse
import com.kw.api.domain.question.service.QuestionService
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BundleService(
    private val bundleRepository: BundleRepository,
    private val tagRepository: TagRepository,
    private val questionRepository: QuestionRepository,
    private val questionService: QuestionService
) {

    fun createBundle(request: BundleCreateRequest): BundleGetResponse {
        val tags: List<Tag> = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        val bundle = bundleRepository.save(request.toEntity())
        bundle.updateBundleTags(tags)
        return getBundle(bundle.id!!)
    }

//    fun getMyBundles(
//    ): <List<BundleGetResponse>> {
//    }
//
//    fun getBundles(
//        getCondition: BundleGetCondition,
//        pageCondition: PageCondition
//    ): PageResponse<BundleGetResponse> {
//    }
//
//    fun searchBundles(
//        searchCondition: BundleSearchCondition,
//        pageCondition: PageCondition
//    ): PageResponse<BundleGetResponse> {
//    }

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
