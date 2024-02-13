package com.kw.api.domain.bundle.service

import com.kw.api.domain.bundle.dto.request.BundleCreateRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionAddRequest
import com.kw.api.domain.bundle.dto.request.BundleUpdateRequest
import com.kw.api.domain.bundle.dto.response.BundleGetResponse
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BundleService(
    private val bundleRepository: BundleRepository,
    private val tagRepository: TagRepository
) {

    fun createBundle(request: BundleCreateRequest): BundleGetResponse {
        val foundTags: List<Tag> = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        val createdBundle = bundleRepository.save(request.toEntity())
        foundTags.forEach(createdBundle::addBundleTag)
        return getBundle(createdBundle.id!!)
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
        val bundle = bundleRepository.findWithTagsById(id)
            ?: throw IllegalArgumentException("존재하지 않는 꾸러미입니다.")
        return BundleGetResponse.from(bundle)
    }

    fun updateBundle(id: Long, request: BundleUpdateRequest) {
        val bundle = bundleRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 꾸러미입니다.") }
        bundle.updateNameAndShareType(request.name, request.shareType)

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
        bundle.addQuestion(request.toEntity())
    }
//
//    fun removeQuestion(id: Long, request: BundleQuestionRemoveRequest) {}
//
//    fun updateQuestionOrderList(id: Long, request: BundleQuestionOrderListUpdateRequest) {}

    private fun getExistTags(tagIds: List<Long>): List<Tag> {
        val foundTags = tagRepository.findAllById(tagIds)
        if (foundTags.size != tagIds.size) {
            throw IllegalArgumentException("존재하지 않는 태그가 포함되어 있습니다.")
        }
        return foundTags
    }

}
