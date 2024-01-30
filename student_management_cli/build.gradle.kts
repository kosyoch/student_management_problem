plugins{
    id("java")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}
repositories{
    mavenCentral()
}

dependencies{
    implementation(project(":student_management_core"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}