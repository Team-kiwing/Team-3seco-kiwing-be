package com.kw.infraquerydsl.domain.question

import com.kw.data.domain.question.Question

interface QuestionCustomRepository {
    fun searchQuestion(keyword : String) : List<Question>
}
