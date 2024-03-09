package com.kw.infrasecurity.resolver

import io.swagger.v3.oas.annotations.Parameter

@Parameter(hidden = true)
@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class AuthToMember {
}
