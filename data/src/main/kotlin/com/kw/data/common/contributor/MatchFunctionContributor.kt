package com.kw.data.common.contributor

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.boot.model.FunctionContributor
import org.hibernate.type.StandardBasicTypes.DOUBLE

class MatchFunctionContributor : FunctionContributor {

    companion object {
        private const val FUNCTION_NAME = "MATCH_AGAINST"
        private const val FUNCTION_PATTERN = "MATCH (?1) AGAINST (?2 IN BOOLEAN MODE)"
    }

    override fun contributeFunctions(functionContributions: FunctionContributions) {
        functionContributions.functionRegistry
            .registerPattern(
                FUNCTION_NAME,
                FUNCTION_PATTERN,
                functionContributions.typeConfiguration.basicTypeRegistry.resolve(DOUBLE)
            )
    }
}
