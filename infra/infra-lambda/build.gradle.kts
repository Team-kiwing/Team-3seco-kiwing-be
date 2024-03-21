import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

// 실행가능한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 false로 비활성화함
bootJar.enabled = false
// 외부에서 의존하기 위한 jar로 생성하는 옵션, main이 없는 라이브러리에서는 true로 비활성화함
jar.enabled = true

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(project(":data"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // lambda
    implementation("org.springframework.cloud:spring-cloud-function-web:4.1.0")
    implementation("org.springframework.cloud:spring-cloud-function-kotlin:4.1.0")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:4.1.0")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.4")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("org.springframework.boot.experimental:spring-boot-thin-layout:1.0.28.RELEASE")
}

configurations {
    create("shaded")
}

val shade by tasks.registering(Jar::class) {
    from(configurations["shaded"].map { if (it.isDirectory) it else zipTree(it) })
    configurations["shaded"].forEach { dep ->
        from(dep) {
            exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        }
    }
    archiveClassifier.set("aws")
}

artifacts {
    add("archives", shade)
}
