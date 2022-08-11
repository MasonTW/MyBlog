package com.mx.blog.repository

import com.mx.blog.entity.AgreementHistory
import org.springframework.data.jpa.repository.JpaRepository

interface AgreementHistoryRepository: JpaRepository<AgreementHistory, Long> {

}
