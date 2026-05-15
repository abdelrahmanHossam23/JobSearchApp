# HireTrack — Job Search & Application Tracker

A native Android app that helps job seekers organize their job hunt — search listings, bookmark opportunities, track application progress, and set deadline reminders — all in one place.

## Features

- **Job Search & Filters** — search jobs with filters for contract type, salary range, location, and more
- **Job Details:** View comprehensive job information including title, company, description, and contract details.
- **Bookmark Listings** — save interesting jobs to apply later, stored offline with Room
- **Application Status Tracker** — track each application through stages: Applied → Interviewed → Offered
- **Reminders & Notifications** — set follow-up reminders for interviews and deadlines using WorkManager
- **Offline Support** — bookmarks and tracked applications persist locally via Room database

## Tech Stack

- **Language:** Kotlin
- **Architecture:** Clean Architecture, MVVM, Repository Pattern
- **UI:** Jetpack Compose, Material Design 3
- **Local Storage:** Room Database
- **Dependency Injection:** Dagger Hilt
- **Background Work:** WorkManager (reminders & notifications)
- **Services:** Firebase
- **Build:** Gradle (Kotlin DSL)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/myjobsearchapplication/
│   │   │   ├── data/
│   │   │   │   ├── Constants.kt           # API base URL and constants
│   │   │   │   └── mapper/                # Data to domain model mapping
│   │   │   ├── di/                        # Hilt DI modules (AppModule.kt)
│   │   │   ├── domain/                    # Business/domain logic
│   │   │   ├── ui/
│   │   │   │   ├── common_components/     # JobStatus, NotificationHelper, ThemeManager
│   │   │   │   ├── mapper/                # Domain to UI model mapping and helpers
│   │   │   │   ├── navigation/            # Compose navigation code
│   │   │   │   ├── screens/               # Job search and other screens
│   │   │   │   └── theme/                 # Compose Material themes, colors, typography
│   │   │   ├── MainActivity.kt            # Main activity with splash and theme setup
│   │   │   └── MyApplication.kt           # Hilt & WorkManager app initialization
│   │   └── res/                           # Standard Android resources
├── build.gradle
├── gradle.properties
└── google-services.json                    # Firebase config (if push services enabled)
```

## Getting Started

### Prerequisites

- Android Studio (Giraffe or later recommended)
- Android SDK 33+

### Installation

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/abdelrahmanHossam23/JobSearchApp
    cd JobSearchApp
    ```

2. **Open with Android Studio** and let it sync the Gradle files.

3. **Configure Adzuna API Key and Firebase**
   - Make sure the `google-services.json` file is present in `app/`.
   - Edit API keys in `Constants.kt` as necessary for Adzuna access.

4. **Run the App** on an emulator or physical device running Android 7.0+.



## Author

Abd Elrahman Hossam — [LinkedIn](https://www.linkedin.com/in/abd-elrahman-hossam-b61379283/) · [GitHub](https://github.com/abdelrahmanHossam23)
