package com.kw.api.common.dto

data class ApiResponse<T>(val message: String, val data: T) {
    companion object {
        fun <T> ok(result: T): ApiResponse<T> {
            return ApiResponse("ok", result)
        }

        fun <T> created(result: T): ApiResponse<T> {
            return ApiResponse("created", result)
        }
    }
}
