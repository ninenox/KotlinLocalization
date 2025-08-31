# KotlinLocalization

[![CI](https://github.com/ninenox/KotlinLocalization/actions/workflows/gradle.yml/badge.svg)](https://github.com/ninenox/KotlinLocalization/actions/workflows/gradle.yml)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-blue?logo=kotlin)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.ninenox/kotlin-locale-manager)](https://search.maven.org/artifact/io.github.ninenox/kotlin-locale-manager)

Android kotlin library for change ui language in android application on runtime.

![Alt Text](https://media.giphy.com/media/VEcDJtSPLjQ6X3NRbs/giphy.gif)


# Installation
Add `mavenCentral()` to your repositories and include the dependency in your module build file:

```kotlin
// build.gradle.kts
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.ninenox:kotlin-locale-manager:1.0.1")
}
```

# Getting Started

1. Create class and extend `ApplicationLocale`.

```
class App : ApplicationLocale() {

}
```

2. In `AndroidManifest.xml`
```
<application
        android:name=".App"
        ...
        />
```

3. In the `res` folder add locale-specific resources.

```
values-th
   - strings.xml
values-en
   - strings.xml
```

4. In any `Activity` extend `AppCompatActivityBase`.

```
class MainActivity : AppCompatActivityBase() {

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        ...
        
    }
    
}
```
5. Call the function `setNewLocale("...")` to set the current language and refresh the UI.
```

setNewLocale(LocaleManager.LANGUAGE_ENGLISH) // ตัวอย่าง LocaleManager.LANGUAGE_ENGLISH, LocaleManager.LANGUAGE_THAI, ...

```


6. Get the current language code.
The value of `language` will be a lowercase code such as "en" or "th". It is recommended to access it via `ApplicationLocale.localeManager?.language` to use the locale manager that is shared throughout the app.

```
ApplicationLocale.localeManager?.language // "en"
```

7. Get current `Locale` instance.

`LocaleManager.getLocale(resources)` will return the current `Locale` and can be used for country checks or locale-aware formatting.

```kotlin
val locale = LocaleManager.getLocale(resources)

if (locale.country == "TH") {
    // ประเทศไทย
}

val dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, locale)
val formattedDate = dateFormat.format(java.util.Date())
```
        


## Testing

Run unit tests with Gradle:

```
./gradlew test
```

This command executes the library's test suite.

![Alt Text](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif)


## License

Licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
