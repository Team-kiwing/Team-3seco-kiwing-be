package com.kw.api.common.dto.response

class ErrorResponse(
    val code: String,
    val message: String,
    val validations: MutableMap<String, String> = mutableMapOf()
) {
    fun addValidation(key: String, value: String) {
        validations[key] = value
    }

}
