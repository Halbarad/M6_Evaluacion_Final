# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
<<<<<<< HEAD
#-renamesourcefileattribute SourceFile
=======
#-renamesourcefileattribute SourceFile

# ==============================
# 🚀 Configuración ProGuard/R8 moderna para Kotlin + Retrofit + ViewModel
# ==============================

# Mantener clases de Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Mantener clases usadas por Gson (evita que borre campos JSON)
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Mantener modelos de datos (POJOs)
-keep class cl.unab.modulo5.data.model.** { *; }

# Mantener ViewModels (usados con Reflection)
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Mantener clases de AndroidX Navigation y Fragments
-keep class androidx.navigation.** { *; }
-keep class androidx.fragment.app.** { *; }

# Evitar eliminar anotaciones
-keepattributes *Annotation*

# Evitar warnings innecesarios
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn kotlinx.coroutines.**
>>>>>>> 947a498a266eca5417002272fdbdc3690f57dbbc
