object Dependencies {
    object Kotlin {
        private const val kotlinVersion = "1.5.21"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    }

    object KotlinX {
        private const val coroutinesVersion = "1.5.1"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

        private const val datetimeVersion = "0.2.1"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"
    }

    object Android {
        private const val pluginVersion = "7.0.1"
        const val plugin = "com.android.tools.build:gradle:$pluginVersion"

        private const val materialVersion = "1.4.0"
        const val material = "com.google.android.material:material:$materialVersion"
    }

    object AndroidX {
        private const val appcompatVersion = "1.3.1"
        const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
        private const val constraintVersion = "2.1.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:$constraintVersion"
    }
}