package com.mx.blog.repository

import com.mx.blog.entity.AgreementHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AgreementHistoryRepository: JpaRepository<AgreementHistory, Long> {
    fun findByAgreementUserIdAndArticleId(userId: Long, articleId: Long): Optional<AgreementHistory?>
    fun deleteByAgreementUserIdAndArticleId(userId: Long, articleId: Long)

}
