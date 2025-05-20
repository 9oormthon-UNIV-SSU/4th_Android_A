plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // ❌ JetBrains Compose 플러그인은 Android Compose용 아님, 삭제
    // alias(libs.plugins.kotlin.compose) apply false
}