import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

// 실행가능한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 false로 비활성화함
bootJar.enabled = false
// 외부에서 의존하기 위한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 true로 비활성화함
jar.enabled = true

dependencies {
    implementation(project(":data"))
    implementation(project(":infra:infra-redis"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation ("org.springframework.session:spring-session-data-redis")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}
