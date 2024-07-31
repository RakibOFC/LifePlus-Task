# Technical Assessment

[![Download APK](https://img.shields.io/badge/Download-APK-brightgreen)](https://github.com/RakibOFC/LifePlus-Task/raw/master/LifePlus-Task.apk)
[![App Demo](https://img.shields.io/badge/Watch-Demo-blue)](https://youtu.be/A8NOCuv97DE)

## Download APK

[Download APK](https://github.com/RakibOFC/LifePlus-Task/raw/master/LifePlus-Task.apk)

## [Video Demo](https://www.youtube.com/watch?v=A8NOCuv97DE)

[![Video Demo](https://img.youtube.com/vi/A8NOCuv97DE/0.jpg)](https://www.youtube.com/watch?v=A8NOCuv97DE)

This project demonstrates the creation of an Android application with multiple activities, local database storage, and a simple search functionality. The application follows a clean architecture and employs good design patterns and UI/UX principles, including Material Design, Hilt for dependency injection, and MVVM clean architecture.

## Objectives

- **Splash Activity:** Displayed when the app is launched.
- **Login Activity:** Allows users to log in and includes a link to the registration Activity.
- **Registration Activity:** Allows new users to register.
- **Dashboard Activity:** The main landing page after login.
- **Profile Activity:** Displays user profile details.
- **Details Activity:** Shows detailed content when a search result is clicked.

## Tasks

- **Registration Data Storage:** Registration data is stored in a local database.
- **Login Validation:** The login page validates user information from the local database.
- **Navigation:** Users are directed to the Dashboard after successful login.
- **Search Functionality:** The Dashboard includes a search box. Search results are displayed in the same Activity.
- **Details View:** Clicking on a search result opens the Details Activity, where the user can view the content details.
- **Design Patterns:** Good design patterns are implemented where possible.
- **UI/UX:** The application features a user-friendly interface with good UI/UX principles, utilizing Material Design.

## Design and Architecture

- **Material Design:** The application uses Material Design principles to provide a modern and intuitive user interface.
- **Hilt:** Dependency injection is managed using Hilt for better modularity and ease of testing.
- **MVVM Clean Architecture:** The project follows the MVVM (Model-View-ViewModel) pattern, adhering to clean architecture principles for better separation of concerns and maintainability.

## Submission

- **Public Repository:** The project is available in a public GitHub repository.
- **APK File:** A runnable APK file is included in the repository.
- **Screen Recording:** A screen recording of the app usage is provided. [YouTube/Google Drive Link]

## Getting Started

### Prerequisites

- Android Studio
- Java/Kotlin
- Android SDK

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/RakibOFC/LifePlus-Task.git
    ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or physical device.

### Usage

1. Launch the app to see the Splash Activity.
2. Register a new user in the Registration Activity.
3. Log in using the registered credentials in the Login Activity.
4. Explore the Dashboard Activity, use the search functionality, and view content details in the Details Activity.
5. View user profile details in the Profile Activity.

## Features

- Local database for storing registration data.
- Login validation against stored data.
- Seamless navigation between activities.
- In-app search functionality with real-time results.
- Detailed view of content with a custom view implementation.
- Material Design for enhanced UI/UX.
- Dependency injection using Hilt.
- MVVM Clean Architecture for better code maintainability and separation of concerns.
