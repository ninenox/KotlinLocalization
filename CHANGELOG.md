# Changelog

## [1.1.0] - 2026-04-18

### Added
- **Jetpack Compose support** — `localeAsState()` and `currentLocaleAsState()` composable extensions, `LocalLocaleManager` CompositionLocal
- **Android 13+ Per-App Language** — `setNewLocale()` now calls `AppCompatDelegate.setApplicationLocales()`, integrating with the system *Settings → App language* screen on API 33+
- **`FragmentBase`** — base `Fragment` class that applies the current locale to the Fragment's context, mirroring `AppCompatActivityBase`
- **`StateFlow<Locale>`** — `LocaleManager.currentLocaleFlow` for typed locale observation in ViewModels and Compose
- **`LocaleManager.getLocale(languageTag: String)`** — companion helper to get a `Locale` from a language tag string
- **24 language constants** — added `LANGUAGE_JAPANESE`, `LANGUAGE_KOREAN`, `LANGUAGE_CHINESE_SIMPLIFIED`, `LANGUAGE_CHINESE_TRADITIONAL`, `LANGUAGE_ARABIC`, `LANGUAGE_SPANISH`, `LANGUAGE_FRENCH`, `LANGUAGE_GERMAN`, `LANGUAGE_PORTUGUESE`, `LANGUAGE_PORTUGUESE_BRAZIL`, `LANGUAGE_RUSSIAN`, `LANGUAGE_ITALIAN`, `LANGUAGE_HINDI`, `LANGUAGE_INDONESIAN`, `LANGUAGE_VIETNAMESE`, `LANGUAGE_MALAY`, `LANGUAGE_TURKISH`, `LANGUAGE_DUTCH`, `LANGUAGE_POLISH`, `LANGUAGE_UKRAINIAN`, `LANGUAGE_BENGALI`, `LANGUAGE_FARSI`

### Changed
- `AppCompatActivityBase.setNewLocale()` no longer calls `recreate()` on API 33+ — the system handles recreation automatically
- Updated Kotlin to 2.0.21
- Updated `compose.runtime` (compileOnly) to 1.7.6

## [1.0.1] - Initial release

- `LocaleManager` — stores and applies locale via SharedPreferences and `createConfigurationContext()`
- `ApplicationLocale` — `Application` base class that initializes `LocaleManager`
- `AppCompatActivityBase` — `AppCompatActivity` base class with `setNewLocale()` helper
- Language constants: `LANGUAGE_ENGLISH`, `LANGUAGE_THAI`
