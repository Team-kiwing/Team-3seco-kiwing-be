package com.kw.data.domain.question

import com.kw.data.domain.Base
import com.kw.data.domain.tag.Tag
import jakarta.persistence.*

@Entity
class QuestionTag(question: Question, tag: Tag) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @ManyToOne
    @Column(name = "question_id", nullable = false, updatable = false)
    var question : Question = question

    @ManyToOne
    @Column(name = "tag_id", nullable = false, updatable = false)
    var tag : Tag = tag
}
