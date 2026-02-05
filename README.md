# Modern Android Native Skeleton (Clean Architecture)

A robust, production-ready boilerplate for Android applications built with **Kotlin** and **Jetpack Compose**. This project serves as a foundation for scalable mobile apps, implementing industry-standard best practices in architecture and UI/UX.

## 🚀 Key Features & Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com) Declarative UI with **Adaptive Layout** support for various screen sizes (Mobile, Tablets).
- **Navigation:** Type-safe [Navigation Graph](https://developer.android.com) implementation for seamless, compile-time safe screen transitions.
- **Theming:** Full **Material 3** integration with support for **Light/Dark Mode** and dynamic color palettes.
- **Components:** Developed a dedicated suite of **Reusable Common Components** (Buttons, TextFields, AppBar) to ensure UI consistency and "DRY" code.
- **State Management:** Lifecycle-aware **UI State Management** using `StateFlow` and `collectAsStateWithLifecycle`.
- **Architecture:** Clean Architecture + [MVVM](https://developer.android.com) (Model-View-ViewModel)
- **Dependency Injection:** [Dagger-Hilt](https://developer.android.com)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) for APIs 
- **Asynchronous Work:** [Kotlin Coroutines](https://developer.android.com) & Flow
- **Local Storage:** [Room Database](https://developer.android.com) for local persistence
- **Jetpack Navigation Component:** [Navigation Graph](https://developer.android.com)
- **Adaptive Layout:** [Scaffold Navigator](https://developer.android.com)

## 🏗️ Architecture Overview

The project is structured into three main layers to ensure **Separation of Concerns**:
1. **Data Layer:** Handles API calls and local database operations (Repository Pattern).
2. **Domain Layer:** Contains business logic and UseCases (Pure Kotlin, Framework-independent).
3. **Presentation Layer:** Handles UI state using ViewModels and Jetpack Compose.

## 📱 UI/UX Highlights

- **Adaptive Design:** Utilizes `WindowSizeClass` to provide an optimized experience across different device form factors.
- **Standardized State:** Implements a unified `UIState` to handle Loading, Success, and Error states consistently.
- **Theme Support:** Fully optimized for accessibility with seamless Light and Dark mode switching.

## 🛠️ How to Use

1. Clone the repository: `git clone [https://github.com/rajald/TodoApp.git]`
2. Open in **Android Studio (Ladybug or newer)**.
3. Sync Gradle and run on an emulator or physical device.

## 📈 Proactive Development

*This project was developed as part of a dedicated technical initiative to master high-scale Android system design and modularization.*
