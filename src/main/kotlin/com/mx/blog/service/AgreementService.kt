package com.mx.blog.service

import com.mx.blog.entity.Agreement
import com.mx.blog.entity.AgreementHistory
import com.mx.blog.repository.AgreementHistoryRepository
import com.mx.blog.repository.AgreementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AgreementService(
    private val agreementRepository: AgreementRepository,
    private val agreementHistoryRepository: AgreementHistoryRepository,
) {
    fun agreeArticle(articleId: Long, userId: Long): Agreement {
        return if (!isAgreed(articleId, userId)){
            val newAgreementRecord = AgreementHistory(
                articleId = articleId,
                agreementTime = System.currentTimeMillis().toString(),
                agreementUserId = userId,
            )
            agreementHistoryRepository.save(newAgreementRecord)
            increaseAgreement(articleId)
        } else agreementRepository.findByArticleId(articleId)!!

    }

    private fun increaseAgreement(articleId: Long): Agreement {
        val agreementResult =
            agreementRepository.findByArticleId(articleId) ?: Agreement(articleId = articleId, agreementNum = 0)
        agreementResult.agreementNum++
        return agreementRepository.save(agreementResult)
    }

    fun isAgreed(articleId: Long, userId: Long): Boolean {
        return agreementHistoryRepository.findByAgreementUserIdAndArticleId(userId, articleId).isPresent
    }

    fun cancelArticleAgreement(articleId: Long, userId: Long): Boolean {
        if (isAgreed(articleId,userId)) {
            decreaseAgreementNum(articleId)
            agreementHistoryRepository.deleteByAgreementUserIdAndArticleId(userId, articleId)
        }
        return true
    }

    private fun decreaseAgreementNum(articleId: Long) {
        val agreementResult = agreementRepository.findByArticleId(articleId) ?: Agreement(articleId = articleId, agreementNum = 1 )
        agreementResult.agreementNum--
        agreementRepository.save(agreementResult)
    }
}
