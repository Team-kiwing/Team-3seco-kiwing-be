import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

// 실행가능한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 false로 비활성화함
bootJar.enabled = true
// 외부에서 의존하기 위한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 true로 비활성화함
jar.enabled = false

dependencies {
    implementation(project(":data"))
    implementation(project(":infra:infra-security"))
    implementation(project(":infra:infra-redis"))
    implementation(project(":infra:infra-s3"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

}

