package com.kw.api.domain.claim.service

import com.kw.api.domain.claim.dto.request.CreateClaimRequest
import com.kw.api.domain.claim.dto.response.ClaimResponse
import com.kw.data.domain.claim.Claim
import com.kw.data.domain.claim.repository.ClaimRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ClaimService(val claimRepository: ClaimRepository) {
    fun createClaim(createClaimRequest: CreateClaimRequest) : ClaimResponse {
        val claim = Claim(content = createClaimRequest.content)
        val savedClaim = claimRepository.save(claim)
        return ClaimResponse(id = savedClaim.id, content = savedClaim.content)
    }
}
