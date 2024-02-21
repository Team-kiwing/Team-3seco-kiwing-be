package com.kw.api.common.exception

import com.kw.api.common.dto.response.ErrorResponse
import org.springframework.http.HttpStatus

class ApiException(private val errorCode: ApiErrorCode) : RuntimeException() {

    fun getErrorResponse(): ErrorResponse {
        return ErrorResponse(this.errorCode.code, this.errorCode.message)
    }

    fun getStatus(): HttpStatus {
        return this.errorCode.status
    }

}
