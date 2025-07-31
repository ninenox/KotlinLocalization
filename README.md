

# KotlinLocalization
Android kotlin library for change ui language in android application on runtime.

![Alt Text](https://media.giphy.com/media/VEcDJtSPLjQ6X3NRbs/giphy.gif)


# Installation
On your module app `build.gradle` add
```
dependencies {
     implementation 'com.ninenox.kotlinlocalemanager:kotlin-locale-manager:1.0.0'
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

3. In folder `res` add new locale.

```
values-th
   - strings.xml
values-en
   - strings.xml
```

4. In any `Activity` extend `AppCompatActivityBase` on it.

```
class MainActivity : AppCompatActivityBase() {

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        ...
        
    }
    
}
```
5. Call funtion `setNewLocale("...")` for set current language and refresh UI.
```

setNewLocale(LocaleManager.LANGUAGE_ENGLISH) // ตัวอย่าง LocaleManager.LANGUAGE_ENGLISH, LocaleManager.LANGUAGE_THAI, ...

```


6. Get current language code string.
ค่าของ `language` จะเป็นรหัสภาษาตัวพิมพ์เล็ก เช่น "en" หรือ "th" และแนะนำให้เข้าถึงผ่าน `ApplicationLocale.localeManager?.language` เพื่อใช้ตัวจัดการที่แชร์ทั่วทั้งแอป

```
ApplicationLocale.localeManager?.language // "en"
```
        


![Alt Text](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif)

