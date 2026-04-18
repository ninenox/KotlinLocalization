# Contributing

Thank you for your interest in contributing!

## Getting started

1. Fork the repository and create a branch from `master`.
2. Open the project in Android Studio (Hedgehog or later).
3. Make your changes in the `kotlinlocalemanager` module.
4. Run the tests before submitting: `./gradlew :kotlinlocalemanager:test`

## Pull requests

- Keep PRs focused — one feature or fix per PR.
- Add or update tests for any changed behavior.
- Update `CHANGELOG.md` under `[Unreleased]` with a short description of your change.

## Reporting issues

Use the GitHub issue templates:
- **Bug report** — for unexpected behavior
- **Feature request** — for new ideas

## Adding a new language constant

Add it to the `companion object` in `LocaleManager.kt` following the existing pattern:

```kotlin
const val LANGUAGE_SWAHILI = "sw"
```

Use the [BCP 47 language tag](https://tools.ietf.org/html/bcp47) format (`"zh-CN"` for regional variants).

## Code style

This project follows the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html). Run `./gradlew ktlintCheck` if ktlint is configured, otherwise keep formatting consistent with existing code.
