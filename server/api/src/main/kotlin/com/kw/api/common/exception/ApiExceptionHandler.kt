package com.kw.api.common.exception

import com.kw.api.common.dto.response.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.security.SignatureException

private val logger = KotlinLogging.logger {}

@RestControllerAdvice("com.kw")
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.BAD_REQUEST
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.warn(ex.message, ex)

        val errorCode = ApiErrorCode.BAD_REQUEST
        val errorResponse = ErrorResponse(errorCode.code, errorCode.message)

        ex.bindingResult.fieldErrors.forEach {
            errorResponse.addValidation(it.field, it.defaultMessage!!)
        }

        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.ACCESS_DENIED
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(SignatureException::class)
    protected fun handleSignatureException(ex: SignatureException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.ACCESS_TOKEN_MALFORMED
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(MalformedJwtException::class)
    protected fun handleMalformedJwtException(ex: MalformedJwtException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.ACCESS_TOKEN_MALFORMED
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    protected fun handleExpiredJwtException(ex: ExpiredJwtException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.ACCESS_TOKEN_EXPIRED
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    protected fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        val errorCode = ApiErrorCode.BAD_REQUEST
        val errorResponse = ErrorResponse(errorCode.code, ex.message ?: errorCode.message)
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(ApiException::class)
    protected fun handleApiException(ex: ApiException): ResponseEntity<ErrorResponse> {
        logger.warn(ex.message, ex)
        return ResponseEntity.status(ex.getStatus()).body(ex.getErrorResponse())
    }

}
