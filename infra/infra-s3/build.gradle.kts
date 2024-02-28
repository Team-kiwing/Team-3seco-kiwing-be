import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

// 실행가능한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 false로 비활성화함
bootJar.enabled = false
// 외부에서 의존하기 위한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 true로 비활성화함
jar.enabled = true

dependencies {
    // spring-cloud-aws 관련 라이브러리 버전 관리
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.0"))

    // aws - autoconfigure & s3
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
}
