package com.kw.api.common.exception

import org.springframework.http.HttpStatus

enum class CustomErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {

    // bundle
    FORBIDDEN_BUNDLE(HttpStatus.FORBIDDEN, "FORBIDDEN_BUNDLE", "비공개 꾸러미입니다."),
    NOT_FOUND_BUNDLE(HttpStatus.NOT_FOUND, "NOT_FOUND_BUNDLE", "존재하지 않는 꾸러미입니다."),

    // question
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "NOT_FOUND_QUESTION", "존재하지 않는 질문입니다."),
    INCLUDE_NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "INCLUDE_NOT_FOUND_QUESTION", "존재하지 않는 질문이 포함되어 있습니다."),

    // tag
    INCLUDE_NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "INCLUDE_NOT_FOUND_TAG", "존재하지 않는 태그가 포함되어 있습니다."),

    // common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),

}
