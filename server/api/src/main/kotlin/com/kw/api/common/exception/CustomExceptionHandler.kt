package com.kw.api.common.exception

import com.kw.api.common.dto.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorCode = CustomErrorCode.BAD_REQUEST
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorCode = CustomErrorCode.BAD_REQUEST
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message)

        ex.bindingResult.fieldErrors.forEach {
            errorResponse.addValidation(it.field, it.defaultMessage!!)
        }

        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(CustomException::class)
    protected fun handleCustomException(ex: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(ex.getStatus()).body(ex.getErrorResponse())
    }

}
