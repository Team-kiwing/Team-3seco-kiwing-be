package com.kw.data.domain.bundle.dto.request

data class BundleGetCondition(
    val sortingType: String?,
) {
    enum class SortingType {
        LATEST,
        CREATED,
        UPDATED;

        companion object {
            fun from(input: String): SortingType {
                try {
                    return valueOf(input.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException("존재하지 않는 정렬 타입입니다: $input")
                }
            }
        }
    }
}
