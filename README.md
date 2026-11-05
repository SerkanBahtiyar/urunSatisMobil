# Local Products Marketplace — Android

An Android application concept for presenting and managing locally produced products.

## Overview

The application provides user authentication and a product-listing flow. Product information is retrieved from Cloud Firestore and displayed in an Android interface. Authenticated users can access the product submission flow.

## Technology Stack

- Java
- Android SDK
- AndroidX
- Firebase Authentication
- Cloud Firestore
- RecyclerView

## Main Areas

- User registration and sign-in
- Product listing
- Product information display
- Adding a new product
- Firebase-backed data retrieval
- Sign-out flow

## Getting Started

1. Clone this repository.
2. Open the `ordu` folder in Android Studio.
3. Connect the project to a Firebase project that you control.
4. Enable the Firebase Authentication provider(s) required by the app.
5. Create and configure a Cloud Firestore database with the collection and fields expected by the application.
6. Add your own Firebase configuration file to the Android app module.
7. Sync Gradle and run the application on an emulator or Android device.

> Do not publish Firebase configuration files containing project-specific identifiers if your project policy requires them to remain private. Never commit service-account keys or other privileged credentials.

## Notes

The app requires a separately configured Firebase backend. No public APK or hosted backend is documented in this repository.

## Author

**Serkan Bahtiyar** — Computer Engineer

- Portfolio: https://serkanbahtiyar.github.io/
- GitHub: https://github.com/SerkanBahtiyar
- LinkedIn: https://www.linkedin.com/in/serkanbahtiyar/
