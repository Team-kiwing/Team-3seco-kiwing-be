package com.kw.data.domain.tag.repository

import com.kw.data.domain.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long> {
}
