# Keep all public API classes so minification doesn't break apps using this library
-keep class io.github.ninenox.kotlinlocalemanager.LocaleManager { *; }
-keep class io.github.ninenox.kotlinlocalemanager.ApplicationLocale { *; }
-keep class io.github.ninenox.kotlinlocalemanager.AppCompatActivityBase { *; }
-keep class io.github.ninenox.kotlinlocalemanager.FragmentBase { *; }

# Keep Kotlin metadata so reflection-based tools (e.g. Compose, serialization) work correctly
-keepattributes RuntimeVisibleAnnotations
-keepattributes *Annotation*
