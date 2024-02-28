package com.kw.infras3.service

import io.awspring.cloud.s3.ObjectMetadata
import io.awspring.cloud.s3.S3Resource
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*


@Service
class S3Service (
    @Value("\${aws.s3.bucket}") private val bucket: String,
    private val s3Template: S3Template) {

    @Throws(IOException::class)
    fun uploadThumbnail(image: MultipartFile): String {
        val originalFilename: String? = image.getOriginalFilename() // 클라이언트가 전송한 파일 이름
        val extension: String? = StringUtils.getFilenameExtension(originalFilename) // 파일 확장자
        val uuidImageName = UUID.randomUUID().toString() + "." + extension // 파일 이름 중복 방지

        // S3에 파일 업로드
        val s3Resource: S3Resource = s3Template.upload(
            bucket,
            uuidImageName,
            image.getInputStream(),
            ObjectMetadata.builder().contentType(extension).build()
        )

        return s3Resource.getURL().toString()
    }
}
