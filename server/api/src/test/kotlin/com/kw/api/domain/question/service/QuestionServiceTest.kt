package com.kw.api.domain.question.service

import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class QuestionServiceTest @Autowired constructor(val questionService: QuestionService,
                                                 val questionRepository: QuestionRepository,
                                                 val tagRepository: TagRepository) {
    @Test
    fun 질문_생성_성공() {
        // given
        val tag1 = tagRepository.save(Tag("백엔드"))
        val tag2 = tagRepository.save(Tag("프론트엔드"))

        val questionCreateRequest = QuestionCreateRequest(
            content = "test",
            shareStatus = Question.ShareStatus.AVAILABLE,
            originId = null,
            tagIds = listOf(tag1.id, tag2.id)
        )

        // when
        val questionResponse = questionService.createQuestion(questionCreateRequest = questionCreateRequest)

        // then
        val question = questionRepository.findById(questionResponse.id!!).orElseThrow {RuntimeException()}
        Assertions.assertEquals(question.id, questionResponse.id)
        Assertions.assertEquals(question.content, questionResponse.content)
        val tagIds = question.questionTags!!.map { questionTag ->
            questionTag.tag.id
        }
        Assertions.assertEquals(tagIds, questionResponse.tagIds)
    }
}
