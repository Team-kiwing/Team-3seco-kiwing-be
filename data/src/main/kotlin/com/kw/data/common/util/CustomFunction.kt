package com.kw.data.common.util

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringPath

class CustomFunction {
    companion object {
        fun match(target: StringPath, keyword: String): BooleanExpression? {
            if (keyword.isEmpty()) {
                return null
            }
            return Expressions.numberTemplate(
                java.lang.Double::class.java,
                "FUNCTION('MATCH_AGAINST', {0}, {1})",
                target,
                keyword.trim().split(" ").joinToString(" ") { "+$it" }
            ).gt(0)
        }
    }
}
