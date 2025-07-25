# 📱 Android JSON Hierarchy Viewer

An Android app built with [Jetpack Compose](https://developer.android.com/jetpack/compose) and [Kotlin](https://kotlinlang.org/) that fetches and displays a hierarchical JSON structure from a remote API. The app supports offline caching, image previews, and interactive UI navigation based on the hierarchy.

## 🌐 Overview

This project is part of a coding challenge focused on building a **structured**, **maintainable**, and **user-friendly** Android application. It fetches JSON data containing nested **Pages**, **Sections**, and **Questions**, and presents them with UI elements reflecting their respective hierarchy levels.

| Feature | Status |
|--------|--------|
| Fetch remote JSON | ✅ |
| Hierarchical rendering (Page > Section > Question) | ✅ |
| Font size based on depth | ✅ |
| Offline caching via database | ✅ |
| Text and image question handling | ✅ |
| Full-size image screen | ✅ |
| Graceful network fallback | ✅ |

## 🚀 Features

- 📄 Displays nested **Pages**, **Sections**, and **Questions**
- 🔠 Font size reflects content hierarchy
- 🖼️ Image questions are fetched, resized, and clickable to view full-screen
- 📡 Simulated network fetching with offline persistence
- 💾 Offline support using [Room](https://developer.android.com/jetpack/androidx/releases/room)
- ⚙️ Modular architecture using [Jetpack Navigation 3](https://developer.android.com/jetpack/androidx/releases/navigation3) (alpha)

## 📦 Tech Stack

| Tool/Library | Purpose |
|--------------|---------|
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | Modern declarative UI |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Asynchronous programming |
| [Room](https://developer.android.com/jetpack/androidx/releases/room) | Local database for offline caching |
| [Jetpack Navigation 3 (Alpha)](https://developer.android.com/jetpack/androidx/releases/navigation3) | Navigation between screens with predictive back support for sdk 36 |
| [Coil](https://coil-kt.github.io/coil/) | Image loading in Compose |
| [Retrofit](https://square.github.io/retrofit/) | Networking and API service simulation |

## 🗂️ Architecture

This project follows **MVVM (Model-View-ViewModel)** with a clean separation of concerns:

- `data` – Networking, caching, and DTOs
- `domain` – Models and business logic
- `ui` – Composables and navigation
- `di` – Dependency injection manually

## 📲 How to Run

### Prerequisites

- Android Studio Meerkat or newer
- Android SDK 34+
- Kotlin 1.9+
- Gradle 8.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/json-hierarchy-viewer.git
   cd json-hierarchy-viewer
2. Open the project in Android Studio
3. Run the app on an emulator or device

### Api
The JSON is fetched from:

https://mocki.io/v1/f118b9f0-6f84-435e-85d5-faf4453eb72a

### Sample structure:
{
  "items": [
    {
      "type": "page",
      "title": "Page 1",
      "items": [
         ...
      ]
    }
  ]
}

## 🌐 Offline Support
All JSON content is cached locally using Room. On app restart or when offline, the cached content is used to rehydrate the UI. This ensures seamless user experience even in low connectivity scenarios.

## 🌐 Design Decisions
Compose was chosen for declarative UI and scalability.

Room provides a robust local persistence solution with minimal boilerplate.

Navigation 3 is used experimentally to embrace new APIs.

Font sizes dynamically scale based on hierarchy depth using a recursive UI builder.

Graceful error handling shows cached content or fallback messages.

## 🌐 Connectivity Fallback
If the app fails to reach the API due to a network issue:
It automatically loads the last cached data.
