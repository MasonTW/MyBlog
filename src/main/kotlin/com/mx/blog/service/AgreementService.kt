package com.mx.blog.service

import com.mx.blog.entity.Agreement
import com.mx.blog.entity.AgreementHistory
import com.mx.blog.repository.AgreementHistoryRepository
import com.mx.blog.repository.AgreementRepository
import org.springframework.stereotype.Service

@Service
class AgreementService(
    private val agreementRepository: AgreementRepository,
    private val agreementHistoryRepository: AgreementHistoryRepository,
) {
    fun agreeArticle(articleId: Long, userId: Long): Agreement {
        val newAgreementRecord = AgreementHistory(
            articleId = articleId,
            agreementTime = System.currentTimeMillis().toString(),
            agreementUserId = userId,
        )
        agreementHistoryRepository.save(newAgreementRecord)

        val agreementResult = agreementRepository.findByArticleId(articleId) ?: Agreement(articleId = articleId, agreementNum = -1 )
        agreementResult.agreementNum++
        return agreementRepository.save(agreementResult)
    }

}
