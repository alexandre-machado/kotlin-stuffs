plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.10")
}

application {
    mainClass.set("com.github.alexandremachado.benchmark.MainKt")
}

benchmark {
    configurations {
        named("main") {
            iterations = 5
            warmups = 3
            outputTimeUnit = "ms"
        }
    }
    targets {
        register("main") {
            this as kotlinx.benchmark.gradle.JvmBenchmarkTarget
            jmhVersion = "1.37"
        }
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}
