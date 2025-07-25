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
- ⚙️ Modular architecture using [Jetpack Navigation 3](https://developer.android.com/jetpack/androidx/releases/navigation) (alpha)

## 📦 Tech Stack

| Tool/Library | Purpose |
|--------------|---------|
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | Modern declarative UI |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Asynchronous programming |
| [Room](https://developer.android.com/jetpack/androidx/releases/room) | Local database for offline caching |
| [Jetpack Navigation 3 (Alpha)](https://developer.android.com/jetpack/androidx/releases/navigation3) | Navigation between screens |
| [Coil](https://coil-kt.github.io/coil/) | Image loading in Compose |
| [Retrofit](https://square.github.io/retrofit/) | Networking and API service simulation |
| [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) | Dependency Injection |

## 🗂️ Architecture

This project follows **MVVM (Model-View-ViewModel)** with a clean separation of concerns:

- `data` – Networking, caching, and DTOs
- `domain` – Models and business logic
- `ui` – Composables and navigation
- `di` – Dependency injection setup using Hilt

## 🧪 Testing

- Unit tests available for ViewModels and data layer
- Offline caching and JSON parsing are covered by integration tests

## 📲 How to Run

### Prerequisites

- Android Studio Giraffe or newer
- Android SDK 34+
- Kotlin 1.9+
- Gradle 8.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/json-hierarchy-viewer.git
   cd json-hierarchy-viewer
