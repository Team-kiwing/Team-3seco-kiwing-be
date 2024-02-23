package com.kw.api.domain.claim.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.claim.dto.request.CreateClaimRequest
import com.kw.api.domain.claim.dto.response.ClaimResponse
import com.kw.api.domain.claim.service.ClaimService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "건의")
@RestController
@RequestMapping("/api/v1")
class ClaimController(val claimService: ClaimService) {

    @Operation(summary = "건의 생성")
    @PostMapping("/claims")
    fun createClaim(@RequestBody createClaimRequest: CreateClaimRequest): ApiResponse<ClaimResponse> {
        val response = claimService.createClaim(createClaimRequest)
        return ApiResponse.created(response)
    }
}
