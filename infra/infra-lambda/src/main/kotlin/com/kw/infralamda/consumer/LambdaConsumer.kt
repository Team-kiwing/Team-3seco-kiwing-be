package com.kw.infralamda.consumer

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.repository.QuestionRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow

@Component
@Transactional
class LambdaConsumer(private val questionRepository: QuestionRepository,
                     private val bundleRepository: BundleRepository)
{
    private val G = 0.8
    private val EXPOSECOUNT_WEIGHT = 1
    private val SHARECOUNT_WEIGHT = 1
    private val VIEWCOUNT_WEIGHT = 1
    private val SCRAPCOUNT_WEIGHT = 1

    @Bean
    fun batchPopularity(): () -> Unit {
        return {
            updateQuestionPopularity()
            updateBundlesPopularity()
        }
    }

    private fun updateBundlesPopularity() {
        val bundles = bundleRepository.findAll()
        bundles.forEach { bundle ->
            val hours = ChronoUnit.HOURS.between(bundle.createdAt, LocalDateTime.now()).toDouble()
            val popularity = getBundlePopularity(bundle, hours)
            bundle.updatePopularity(popularity)
        }
    }

    private fun getBundlePopularity(bundle: Bundle, hours: Double) =
        (bundle.viewCount * VIEWCOUNT_WEIGHT + bundle.scrapeCount * SCRAPCOUNT_WEIGHT) / hours.pow(G)

    private fun updateQuestionPopularity() {
        val questions = questionRepository.findAll()
        questions.forEach { question ->
            val hours = ChronoUnit.HOURS.between(question.createdAt, LocalDateTime.now()).toDouble()
            val popularity = getQuestionPopularity(question, hours)
            question.updatePopularity(popularity)
        }
    }

    private fun getQuestionPopularity(question: Question, hours: Double) =
        (question.exposeCount * EXPOSECOUNT_WEIGHT + question.shareCount * SHARECOUNT_WEIGHT) / hours.pow(G)
}
