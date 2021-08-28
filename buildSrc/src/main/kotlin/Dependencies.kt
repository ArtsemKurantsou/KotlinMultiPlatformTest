object Dependencies {
    object Kotlin {
        private const val kotlinVersion = "1.5.30"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
    }

    object KotlinX {
        private const val coroutinesVersion = "1.5.1-native-mt"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

        private const val datetimeVersion = "0.2.1"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"

        private const val serializationJsonVersion = "1.2.2"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationJsonVersion"
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

    object SqlDelight {
        private const val sqlDelightVersion = "1.5.0"
        const val plugin = "com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion"
        const val runtime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
    }

    object Ktor {
        private const val ktorVersion = "1.6.2"
        const val core = "io.ktor:ktor-client-core:$ktorVersion"
        const val android = "io.ktor:ktor-client-android:$ktorVersion"
        const val iOS = "io.ktor:ktor-client-ios:$ktorVersion"
        const val serialization = "io.ktor:ktor-client-serialization:$ktorVersion"
    }
}