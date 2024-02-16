package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import org.springframework.data.jpa.repository.JpaRepository

interface BundleRepository : JpaRepository<Bundle, Long>, BundleCustomRepository {}
