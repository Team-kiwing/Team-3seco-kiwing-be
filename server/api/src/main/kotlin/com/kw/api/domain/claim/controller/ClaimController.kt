package com.kw.api.domain.claim.controller

import com.kw.api.domain.claim.dto.request.CreateClaimRequest
import com.kw.api.domain.claim.dto.response.ClaimResponse
import com.kw.api.domain.claim.service.ClaimService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "건의")
@RestController
@RequestMapping("/api/v1")
class ClaimController(val claimService: ClaimService) {

    @Operation(summary = "건의 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/claims")
    fun createClaim(@RequestBody createClaimRequest: CreateClaimRequest): ClaimResponse {
        return claimService.createClaim(createClaimRequest)
    }
}
