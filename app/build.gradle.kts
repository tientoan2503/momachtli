plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.androidx.navigation.safeargs.kotlin)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.dagger.hilt.android)
}

val versionAppName = "1.0.0"
fun generateVersionCode(): Int = versionAppName.replace(".", "").toInt()

android {
  namespace = "com.tientoan.rikka.momachtli"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.tientoan.rikka.momachtli"
    minSdk = 24
    targetSdk = 34
    versionCode = generateVersionCode()
    versionName = versionAppName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.bundles.androidx.navigation)
  // Dagger Hilt
  implementation(libs.dagger.hilt.android)
  ksp(libs.dagger.hilt.compiler)

  // Network
  implementation(libs.bundles.retrofit.okhttp3)
  implementation(libs.bundles.moshi)

  // Room Database
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  // Timber
  implementation(libs.timber)

  // Glide
  implementation(libs.glide)
  ksp(libs.glide.compiler)
  implementation(libs.glide.intergration)
}
