// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.2" apply false
    id("io.gitlab.arturbosch.detekt").version("1.23.3")
}

detekt {
    toolVersion = "1.23.3"
    config.from(files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.3")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-cli:1.23.3")
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
