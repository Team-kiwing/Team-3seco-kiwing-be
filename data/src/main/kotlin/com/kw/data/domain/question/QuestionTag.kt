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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    var question : Question = question

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    var tag : Tag = tag
}
