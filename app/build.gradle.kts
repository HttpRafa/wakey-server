plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:" + findProperty("lombok_version"))
	annotationProcessor("org.projectlombok:lombok:" + findProperty("lombok_version"))

    implementation("org.jetbrains:annotations:" + findProperty("annotations_version"))
    implementation("com.google.code.gson:gson:" + findProperty("gson_version"))
    implementation("com.google.guava:guava:" + findProperty("guava_version"))

    implementation("org.slf4j:slf4j-api:" + findProperty("slf4j_version"))
    implementation("org.slf4j:slf4j-simple:" + findProperty("slf4j_version"))
    implementation("com.sparkjava:spark-core:" + findProperty("spark_version"))
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "de.rafael.wakey.WakeyApplication"
    }
    dependsOn("shadowJar")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
