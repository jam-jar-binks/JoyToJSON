import com.sun.tools.javac.Main.compile

plugins {
    id("java")
}

group = "org.joytojson"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("github.com/gradle/kotlin-dsl ")

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.kwhat:jnativehook:2.2.2")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")
    implementation("net.java.jinput:jinput:2.0.10")


}

tasks.test {
    useJUnitPlatform()
}