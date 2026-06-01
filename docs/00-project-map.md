# Project Map

## Current State

This repository is a single-module Android app.

```text
FirebaseDemo
  app
    src
      main
        java/com/example/firebasedemo
          MainActivity.kt
          ui/theme
        res
      test
      androidTest
  gradle
    libs.versions.toml
  constraints.md
```

## Current Entry Point

`MainActivity` hosts a Compose UI through `setContent`. The current screen is still the generated starter greeting. Future rounds will replace this with app navigation.

## Current Dependencies

The project now has the dependencies needed for the planned first version:

```text
Compose Material3
Navigation Compose
Lifecycle ViewModel Compose
Lifecycle Runtime Compose
Hilt
Room
Firebase Analytics
Firebase Remote Config
Kotlin serialization
Retrofit and OkHttp
```

No additional dependency is required for the first product version.

## Planned Package Layout

```text
com.example.firebasedemo
  core
    analytics
    remoteconfig
    time
  data
    local
    mapper
    repository
  domain
    model
    repository
    usecase
  feature
    analyticslab
    habitsetup
    home
    onboarding
    progress
  navigation
  ui
    theme
```

## Boundary Rules

Domain code must not depend on Android, Firebase, Room, Hilt, or Compose.

Data code implements domain repository interfaces and owns local persistence details.

Feature code owns ViewModels, UI state, and Compose screens.

Core analytics and Remote Config wrappers isolate Firebase SDK calls from the rest of the app.

