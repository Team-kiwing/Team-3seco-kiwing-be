package com.kw.infraquerydsl.domain.question.dto

data class QuestionSearchDto(val keyword : String,
                             val page : Long,
                             val size : Long)
