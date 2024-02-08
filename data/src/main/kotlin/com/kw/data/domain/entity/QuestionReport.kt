package com.kw.data.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class QuestionReport(reason : String, question: Question) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
    var reason : String? = reason
    @ManyToOne
    var question : Question = question
}
