package com.kw.infraquerydsl.domain.question

import com.kw.data.domain.question.Question
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto

interface QuestionCustomRepository {
    fun searchQuestion(questionSearchDto: QuestionSearchDto) : List<Question>
}
