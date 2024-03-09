package com.kw.data.domain.question

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class QuestionReport(reason : String, question: Question) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "reason", nullable = false, updatable = false)
    var reason : String? = reason

    @ManyToOne
    @JoinColumn(name = "question_id")
    var question : Question = question
}
