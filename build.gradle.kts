import com.diffplug.gradle.spotless.SpotlessApply
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.detekt) apply false
}

// Apply Spotless to all sub-projects/modules
val ktlintVersion = libs.versions.ktlint.get()
subprojects {
  apply<SpotlessPlugin>()
  configure<SpotlessExtension> {
    kotlin {
      target("src/**/*.kt")
      targetExclude("build/**/*.kt")
      ktlint(ktlintVersion)
    }

    kotlinGradle {
      target("*.kts")
      ktlint(ktlintVersion)
    }

    // Apply Spotless after every running app
    afterEvaluate {
      tasks.withType<KotlinCompile> {
        finalizedBy("spotlessApply")
      }
    }
  }

  // Apply the detekt plugin to all subprojects
  apply<DetektPlugin>()
  configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    source.from(files("src/"))
    config.from(files("${rootProject.projectDir}/config/detekt/detekt.yml"))
    allRules = true
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
  }
  afterEvaluate {
    dependencies {
      "detektPlugins"(rootProject.libs.detekt)
    }
    // Run detekt after spotlessApply
    tasks.withType<SpotlessApply> {
      finalizedBy("detekt")
    }
  }
}
