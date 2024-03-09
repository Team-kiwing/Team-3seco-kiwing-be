package com.kw.data.domain.claim.repository

import com.kw.data.domain.claim.Claim
import org.springframework.data.jpa.repository.JpaRepository

interface ClaimRepository : JpaRepository<Claim, Long>{
}
