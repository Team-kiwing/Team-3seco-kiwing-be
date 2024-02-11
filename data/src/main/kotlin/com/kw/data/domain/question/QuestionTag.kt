package com.kw.data.domain.question

import com.kw.data.domain.tag.Tag
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class QuestionTag(question: Question, tag: Tag) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
    @ManyToOne
    var question : Question = question
    @ManyToOne
    var tag : Tag = tag
}
