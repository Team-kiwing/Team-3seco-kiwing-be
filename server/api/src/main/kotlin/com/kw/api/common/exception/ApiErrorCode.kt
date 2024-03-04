package com.kw.api.common.exception

import org.springframework.http.HttpStatus

enum class ApiErrorCode(
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
    OVER_QUESTION_LIMIT(HttpStatus.BAD_REQUEST, "OVER_QUESTION_LIMIT", "질문은 최대 100개까지 등록할 수 있습니다."),

    // tag
    INCLUDE_NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "INCLUDE_NOT_FOUND_TAG", "존재하지 않는 태그가 포함되어 있습니다."),

    // member
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "NOT_FOUND_MEMBER", "존재하지 않는 회원입니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "NICKNAME_ALREADY_EXISTS", "이미 존재하는 닉네임입니다."),
    MEMBER_WITHDRAWN(HttpStatus.BAD_REQUEST, "MEMBER_WITHDRAWN", "탈퇴한 회원입니다."),

    // auth
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED", "리프레시 토큰이 만료되었습니다. 다시 로그인 해주세요."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "401/0002", "어세스 토큰이 만료되었으니 재발급 해주세요"),
    ACCESS_TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "401/0003", "올바르지 않은 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "403/0001", "접근이 거부되었습니다."),

    // common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없습니다."),

}
