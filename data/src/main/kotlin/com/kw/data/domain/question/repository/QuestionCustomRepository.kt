package com.kw.data.domain.question.repository

import com.kw.data.domain.question.Question

interface QuestionCustomRepository {
    fun searchQuestion(keyword: String): List<Question>
}
