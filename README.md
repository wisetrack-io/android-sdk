=======
# WiseTrack SDK for Android

The WiseTrack SDK is a powerful analytics tool for tracking user interactions, events, and application metrics in Android applications. It provides a simple interface to initialize tracking, log events, manage SDK settings, and retrieve analytics data such as Advertising ID (ADID) and referrer information.

## Table of Contents

- Features
- Permissions
- Requirements
- Installation
- Dependencies for Features
- Setup
- Usage
  - Initialization
  - Starting and Stopping Tracking
  - Logging Events
  - Managing SDK Settings
  - Retrieving Analytics Data
  - Setting FCM Token
  - Cleaning Up
- Example
- Troubleshooting
- License

## Features

- Track user interactions and custom events (e.g., user actions, revenue events).
- Support for multiple app stores (e.g., Google Play, CafeBazaar, Myket).
- Configurable environments (Sandbox, Production) and SDK settings.
- Retrieve Advertising ID (ADID) and referrer information.
- Integration with Firebase Installation for firebase install id.
- Support for Open Advertising ID (OAID) and Huawei Ads Identifier.
- Referrer tracking for Google Play and CafeBazaar.
- Robust logging with customizable log levels.
- Automatic tracking with configurable delays.

## Requirements

- **Android API Level**: 21 (Lollipop) or higher
- **Kotlin**: 1.9.0 or higher
- **Dependencies**: See the [Dependencies for Features](#dependencies-for-features) section for required and optional dependencies.


## Permissions
- To give the WiseTrack SDK access to device information, you need to declare which permissions your app requires. To do this, add permissions to your `AndroidManifest.xml` file:

  - Add the following permissions to get access to online features:

    ```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    ```
  - If your app doesn't target the Google Play Store, add the following permission to access the device's network state:

    ```xml
      <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
      <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    ```


## Installation

1. **Add the WiseTrack SDK to your project**:

   - Add the dependency to your `app/build.gradle`:

     ```gradle
     implementation 'io.wisetrack:sdk:core:2.0.0' // Replace with the latest version
     ```

   - Sync your project with Gradle.

2. **Add feature-specific dependencies**: Depending on the features you want to enable (e.g., OAID, referrer tracking), add the corresponding dependencies as described in the [Dependencies for Features](#dependencies-for-features) section.


## Dependencies for Features

The WiseTrack SDK automatically utilizes certain features if their corresponding dependencies are included in your project. Below is a list of features and the required dependencies:

- **Open Advertising ID (OAID)**: Enables support for `oaidEnabled` in `WTInitialConfig` to use OAID as an alternative to ADID for Chinese phones that has not GooglePlay Service.
  ```gradle
  implementation 'io.wisetrack:sdk:oaid:2.0.0' // Replace with the latest version
  ```

- **Referrer Tracking**: Enables `referrerEnabled` in `WTInitialConfig` and `getReferrer()` for tracking install sources.
  - check Google Play referrer [latest version here](https://developer.android.com/google/play/installreferrer/library#dependenciess)
  - check Cafe Bazaar referrer [latest version here](https://github.com/cafebazaar/ReferrerSDK/releases)
  ```gradle
  implementation 'io.wisetrack:sdk:referrer:2.0.0' // Replace with the latest version
  implementation 'com.android.installreferrer:installreferrer:2.2' // For Google Play referrer
  implementation 'com.github.cafebazaar:referrersdk:1.0.2' // For CafeBazaar referrer
  ```

- **Google Advertising ID (ADID)**: Enables `getADID()` for retrieving the Google Advertising ID.
  ```gradle
  implementation 'com.google.android.gms:play-services-ads-identifier:18.2.0'
  ```

- **Huawei Ads Identifier**: Enables ADID retrieval on Huawei devices.
  ```gradle
  implementation 'com.huawei.hms:ads-identifier:3.4.62.300'
  ```

- **Firebase Installation ID (FID)**: Enables firebase installation id for finding unique id for user device.
  ```gradle
  implementation 'com.google.firebase:firebase-installations:17.2.0'
  ```

- **AppSet ID**: Provides additional device identification for analytics.
  ```gradle
  implementation 'com.google.android.gms:play-services-appset:16.1.0'
  ```

**Note**: Ensure you add only the dependencies for the features you need. For example, if you don't use Huawei devices, you can skip `huawei-hms-ads-identifier`. Check the [Setup](#setup) section for configuring these features in `WTInitialConfig`.

## Setup

Before using the WiseTrack SDK, you need to initialize it with a configuration object (`WTInitialConfig`). This typically happens in your `Application` class or main `Activity`.

### Creating WTInitialConfig

The `WTInitialConfig` object defines the SDK's behavior, including the app token, store name, environment, and tracking settings.

```kotlin
import io.wisetrack.wisetrack_core.core.WTInitialConfig
import io.wisetrack.wisetrack_core.core.WTStoreName
import io.wisetrack.wisetrack_core.core.WTUserEnvironment
import io.wisetrack.wisetrack_core.utils.WTLogLevel

val config = WTInitialConfig(
    appToken = "YOUR_APP_TOKEN", // Provided by WiseTrack
    storeName = WTStoreName.PlayStore, // Or CafeBazaar, Myket, etc.
    environment = WTUserEnvironment.PRODUCTION, // Or SANDBOX for testing
    trackingWaitingTime = 5, // Delay in seconds before tracking starts
    startTrackerAutomatically = true, // Automatically start tracking
    oaidEnabled = true, // Enable Open Advertising ID (requires wisetrack-oaid)
    logLevel = WTLogLevel.DEBUG, // Set logging level (DEBUG, INFO, etc.)
    referrerEnabled = true // Enable referrer tracking (requires wisetrack-referrer)
)
```

### Initializing the SDK

Initialize the SDK in your `Application` class or `onCreate` of your main `Activity`:

```kotlin
import io.wisetrack.wisetrack_core.WiseTrack

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val wiseTrack = WiseTrack.getInstance(this)
        wiseTrack.initialize(config)
    }
}
```

Register your `Application` class in `AndroidManifest.xml`:

```xml
<application
    android:name=".MyApplication"
    ...>
</application>
```

## Usage

The `WiseTrack` class provides several public methods for interacting with the SDK. Below is a detailed guide to each method.

### Initialization

- `initialize(initialConfig: WTInitialConfig)`: Initializes the SDK with the provided configuration.

  ```kotlin
  wiseTrack.initialize(config)
  ```

  - Call this method once during application startup.
  - Ensure `initialConfig` includes a valid `appToken`.

### Starting and Stopping Tracking

- `startTracking()`: Starts tracking user activity and events.

  ```kotlin
  wiseTrack.startTracking()
  ```

  - Call this if `startTrackerAutomatically` is false in `WTInitialConfig`.
  - The SDK retries up to 3 times if not initialized, with a 3-second delay between attempts.

- `stopTracking()`: Stops all tracking operations.

  ```kotlin
  wiseTrack.stopTracking()
  ```

  - Use this to pause tracking, e.g., when the user opts out of analytics.

### Logging Events

- `logEvent(event: WTEvent)`: Logs a custom event, such as user actions or revenue transactions.

  ```kotlin
  import io.wisetrack.wisetrack_core.core.WTEvent
  import io.wisetrack.wisetrack_core.core.EventParam
  import io.wisetrack.wisetrack_core.core.RevenueCurrency

  // Default event
  val defaultEvent = WTEvent.defaultEvent(
      name = "button_clicked",
      params = mapOf("button_id" to EventParam("start_button"))
  )
  wiseTrack.logEvent(defaultEvent)

  // Revenue event
  val revenueEvent = WTEvent.revenueEvent(
      name = "purchase",
      currency = RevenueCurrency.USD,
      amount = 9.99,
      params = mapOf("item" to EventParam("premium_subscription"))
  )
  wiseTrack.logEvent(revenueEvent)
  ```

  - Use `WTEvent.defaultEvent` for user actions or system events.
  - Use `WTEvent.revenueEvent` for monetary transactions.

### Managing SDK Settings

- `setEnabled(enabled: Boolean)`: Enables or disables the SDK.

  ```kotlin
  wiseTrack.setEnabled(true) // Enable SDK
  wiseTrack.setEnabled(false) // Disable SDK
  ```

  - Ignored if the SDK is not enabled in the configuration or requires a forced update.

- `setLogLevel(level: WTLogLevel)`: Sets the logging level for SDK logs.

  ```kotlin
  wiseTrack.setLogLevel(WTLogLevel.INFO)
  ```

  - Options: `DEBUG`, `INFO`, `WARNING`, `ERROR`, `NONE`.
  - Use `DEBUG` during development, `INFO` or higher for production.

- `clearDataAndStop()`: Clears all stored data and stops tracking (for testing or resetting).

  ```kotlin
  wiseTrack.clearDataAndStop()
  ```

  - Use with caution, as this deletes all analytics data.

### Retrieving Analytics Data

- `getADID(): String?`: Retrieves the Advertising ID (ADID), if available.

  ```kotlin
  val adid = wiseTrack.getADID()
  println("Advertising ID: $adid")
  ```

  - Returns `null` if the ADID is not available (requires `play-services-ads-identifier` or `huawei-hms-ads-identifier`).

- `getReferrer(): String?`: Retrieves the referrer information, if available.

  ```kotlin
  val referrer = wiseTrack.getReferrer()
  println("Referrer: $referrer")
  ```

  - Returns `null` if referrer tracking is disabled or unavailable (requires `wisetrack-referrer`, `android-installreferrer`, or `cafebazaar-referrersdk`).

### Setting FCM Token

- `setFCMToken(token: String)`: Sets the Firebase Cloud Messaging (FCM) token for push notifications.

  ```kotlin
  wiseTrack.setFCMToken("YOUR_FCM_TOKEN")
  ```

  - Call this when the FCM token is received or refreshed.

### Cleaning Up

- `destroy()`: Cleans up resources and stops tracking.

  ```kotlin
  wiseTrack.destroy()
  ```

  - Call this when the application is terminated or the SDK is no longer needed.

## Example

Below is a complete example demonstrating how to use the WiseTrack SDK in an Android application.

```kotlin
import android.app.Application
import io.wisetrack.wisetrack_core.WiseTrack
import io.wisetrack.wisetrack_core.core.WTInitialConfig
import io.wisetrack.wisetrack_core.core.WTStoreName
import io.wisetrack.wisetrack_core.core.WTUserEnvironment
import io.wisetrack.wisetrack_core.core.WTEvent
import io.wisetrack.wisetrack_core.core.EventParam
import io.wisetrack.wisetrack_core.utils.WTLogLevel

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize WiseTrack
        val config = WTInitialConfig(
            appToken = "YOUR_APP_TOKEN",
            storeName = WTStoreName.PlayStore,
            environment = WTUserEnvironment.PRODUCTION,
            trackingWaitingTime = 5,
            startTrackerAutomatically = true,
            oaidEnabled = true,
            logLevel = WTLogLevel.DEBUG,
            referrerEnabled = true
        )
        val wiseTrack = WiseTrack.getInstance(this)
        wiseTrack.initialize(config)

        // Log a custom event
        val event = WTEvent.defaultEvent(
            name = "app_started",
            params = mapOf("version" to EventParam("1.0.0"))
        )
        wiseTrack.logEvent(event)

        // Set FCM token
        wiseTrack.setFCMToken("YOUR_FCM_TOKEN")
    }
}
```

## Troubleshooting

- **SDK Not Initialized**: Ensure `initialize` is called with a valid `WTInitialConfig` before using other methods.
- **No ADID/Referrer**: Verify that the required dependencies (`play-services-ads-identifier`, `wisetrack-referrer`, etc.) are included and that `oaidEnabled` or `referrerEnabled` is set in `WTInitialConfig`. Check device permissions.
- **Logs Not Visible**: Set `logLevel` to `DEBUG` or `INFO` to see detailed logs.
- **Tracking Not Starting**: Check if `startTrackerAutomatically` is false and call `startTracking` manually.
- **Network Errors**: Ensure the device has internet access and the correct `WTSDKEnvironment` is set in `WTInitialConfig`.

For further assistance, contact the WiseTrack support team at support@wisetrack.io.

## License

The WiseTrack SDK is licensed under the MIT License. See the LICENSE file for details.
