package com.mx.blog.service

import com.mx.blog.RandomData.randomString
import com.mx.blog.entity.Agreement
import com.mx.blog.entity.AgreementHistory
import com.mx.blog.repository.AgreementHistoryRepository
import com.mx.blog.repository.AgreementRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.*
import javax.swing.text.html.Option
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
class AgreementServiceTest {
    @Mock
    private lateinit var mockAgreementRepository: AgreementRepository

    @Mock
    private lateinit var mockAgreementHistoryRepository: AgreementHistoryRepository

    @InjectMocks
    private lateinit var agreementService: AgreementService

    private val articleId = Random.nextLong()
    private val userId = Random.nextLong()


    @Test
    fun `should add agreement number and agreement history successful when user does not agree`() {
        val agreementCount = Random.nextLong()
        val agreement = Agreement(articleId = articleId, agreementNum = agreementCount)
        whenever(mockAgreementRepository.findByArticleId(articleId)).thenReturn(agreement)
        whenever(mockAgreementRepository.save(any())).thenReturn(agreement)

        agreementService.agreeArticle(articleId, userId)

        verify(mockAgreementRepository, times(1)).save(eq(agreement.apply {
            this.agreementNum = this.agreementNum + 1
        }))
    }

    @Test
    fun `should add agreement number failed when user does not agree`() {
        val agreementCount = Random.nextLong()
        val agreement = Agreement(articleId = articleId, agreementNum = agreementCount)
        whenever(mockAgreementRepository.findByArticleId(articleId)).thenReturn(agreement)
        whenever(mockAgreementHistoryRepository.findByAgreementUserIdAndArticleId(userId, articleId)).thenReturn(
            Optional.of(
                AgreementHistory(
                    articleId = articleId,
                    agreementUserId = userId,
                    agreementTime = randomString()
                )
            )
        )

        agreementService.agreeArticle(articleId, userId)

        verify(mockAgreementRepository, never()).save(org.mockito.kotlin.any())
    }


}