# کتابخانه WiseTrack برای اندروید

کتابخانه WiseTrack یک ابزار قدرتمند برای تحلیل و ردیابی تعاملات کاربران، رویدادها و معیارهای برنامه در برنامه‌های اندرویدی است. این کتابخانه رابط کاربری ساده‌ای برای راه‌اندازی ردیابی، ثبت رویدادها، مدیریت تنظیمات SDK و دریافت داده‌های تحلیلی مانند شناسه تبلیغاتی (ADID) و اطلاعات ارجاع (Referrer) ارائه می‌دهد.

## فهرست مطالب

- ویژگی‌ها
- مجوزها
- پیش‌نیازها
- نصب
- وابستگی‌ها برای ویژگی‌ها
- راه‌اندازی
- استفاده
  - مقداردهی اولیه
  - شروع و توقف ردیابی
  - ثبت رویدادها
  - مدیریت تنظیمات SDK
  - دریافت داده‌های تحلیلی
  - تنظیم توکن FCM
  - پاک‌سازی
- مثال
- عیب‌یابی
- مجوز

## ویژگی‌ها

- ردیابی تعاملات کاربران و رویدادهای سفارشی (مانند اقدامات کاربر یا رویدادهای درآمدی).
- پشتیبانی از فروشگاه‌های مختلف برنامه (مانند گوگل پلی، کافه‌بازار، مایکت).
- تنظیمات محیط‌های قابل پیکربندی (Sandbox، Production) و تنظیمات SDK.
- دریافت شناسه تبلیغاتی (ADID) و اطلاعات ارجاع.
- یکپارچگی با Firebase Installation برای دریافت شناسه نصب Firebase.
- پشتیبانی از شناسه تبلیغاتی باز (OAID) و شناسه تبلیغاتی هواوی.
- ردیابی ارجاع برای گوگل پلی و کافه‌بازار.
- ثبت گزارش‌های قوی با سطوح قابل تنظیم.
- ردیابی خودکار با تأخیرهای قابل تنظیم.

## پیش‌نیازها

- **سطح API اندروید**: 21 (لالی‌پاپ) یا بالاتر
- **کاتلین**: نسخه 1.9.0 یا بالاتر
- **وابستگی‌ها**: برای وابستگی‌های مورد نیاز و اختیاری به بخش [وابستگی‌ها برای ویژگی‌ها](#وابستگی‌ها-برای-ویژگی‌ها) مراجعه کنید.

## مجوزها

برای دسترسی کتابخانه WiseTrack به اطلاعات دستگاه، باید مجوزهای مورد نیاز برنامه خود را مشخص کنید. برای این کار، مجوزها را به فایل `AndroidManifest.xml` اضافه کنید:

- برای دسترسی به قابلیت‌های آنلاین، مجوزهای زیر را اضافه کنید:

  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  ```

- اگر برنامه شما فروشگاه گوگل پلی را هدف قرار نمی‌دهد، برای دسترسی به وضعیت شبکه دستگاه، مجوزهای زیر را اضافه کنید:

  ```xml
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  ```

## نصب

1. **اضافه کردن کتابخانه WiseTrack به پروژه**:

   - وابستگی را به فایل `app/build.gradle` اضافه کنید:

     ```gradle
     implementation 'io.wisetrack:wisetrack-core:1.0.0' // نسخه را با آخرین نسخه جایگزین کنید
     ```

   - پروژه خود را با Gradle همگام‌سازی کنید.

2. **اضافه کردن وابستگی‌های خاص ویژگی‌ها**: بسته به ویژگی‌هایی که می‌خواهید فعال کنید (مانند OAID، ردیابی ارجاع)، وابستگی‌های مربوطه را طبق توضیحات بخش [وابستگی‌ها برای ویژگی‌ها](#وابستگی‌ها-برای-ویژگی‌ها) اضافه کنید.

## وابستگی‌ها برای ویژگی‌ها

کتابخانه WiseTrack به‌صورت خودکار از برخی ویژگی‌ها استفاده می‌کند، اگر وابستگی‌های مربوطه در پروژه شما گنجانده شده باشند. در زیر فهرستی از ویژگی‌ها و وابستگی‌های مورد نیاز آورده شده است:

- **شناسه تبلیغاتی باز (OAID)**: پشتیبانی از `oaidEnabled` در `WTInitialConfig` را فعال می‌کند تا از OAID به‌عنوان جایگزین ADID برای گوشی‌های چینی بدون سرویس‌های گوگل پلی استفاده شود.

  ```gradle
  implementation(project(":wisetrack-oaid")) // ماژول یا فایل AAR wisetrack-oaid را اضافه کنید
  ```

- **ردیابی ارجاع**: `referrerEnabled` در `WTInitialConfig` و `getReferrer()` را برای ردیابی منابع نصب فعال می‌کند.

  ```gradle
  implementation(project(":wisetrack-referrer")) // ماژول یا فایل AAR wisetrack-referrer را اضافه کنید
  implementation 'com.android.installreferrer:installreferrer:2.2' // برای ارجاع گوگل پلی
  implementation 'com.github.cafebazaar:referrersdk:1.0.2' // برای ارجاع کافه‌بازار
  ```

- **شناسه تبلیغاتی گوگل (ADID)**: `getADID()` را برای بازیابی شناسه تبلیغاتی گوگل فعال می‌کند.

  ```gradle
  implementation 'com.google.android.gms:play-services-ads-identifier:18.2.0'
  ```

- **شناسه تبلیغاتی هواوی**: بازیابی ADID را در دستگاه‌های هواوی فعال می‌کند.

  ```gradle
  implementation 'com.huawei.hms:ads-identifier:3.4.62.300'
  ```

- **شناسه نصب Firebase (FID)**: شناسه نصب Firebase را برای شناسایی منحصربه‌فرد دستگاه کاربر فعال می‌کند.

  ```gradle
  implementation 'com.google.firebase:firebase-installations:17.2.0'
  ```

- **شناسه AppSet**: شناسایی اضافی دستگاه را برای تحلیل‌ها فراهم می‌کند.

  ```gradle
  implementation 'com.google.android.gms:play-services-appset:16.1.0'
  ```

**توجه**: فقط وابستگی‌های مربوط به ویژگی‌های مورد نیاز خود را اضافه کنید. به‌عنوان مثال، اگر از دستگاه‌های هواوی استفاده نمی‌کنید، می‌توانید `huawei-hms-ads-identifier` را نادیده بگیرید. برای پیکربندی این ویژگی‌ها در `WTInitialConfig` به بخش [راه‌اندازی](#راه‌اندازی) مراجعه کنید.

## راه‌اندازی

قبل از استفاده از کتابخانه WiseTrack، باید آن را با یک شیء پیکربندی (`WTInitialConfig`) مقداردهی کنید. این کار معمولاً در کلاس `Application` یا متد `onCreate` فعالیت اصلی انجام می‌شود.

### ایجاد WTInitialConfig

شیء `WTInitialConfig` رفتار SDK را تعریف می‌کند، شامل توکن برنامه، نام فروشگاه، محیط و تنظیمات ردیابی.

```kotlin
import io.wisetrack.wisetrack_core.core.WTInitialConfig
import io.wisetrack.wisetrack_core.core.WTStoreName
import io.wisetrack.wisetrack_core.core.WTUserEnvironment
import io.wisetrack.wisetrack_core.utils.WTLogLevel

val config = WTInitialConfig(
    appToken = "YOUR_APP_TOKEN", // ارائه‌شده توسط WiseTrack
    storeName = WTStoreName.PlayStore, // یا CafeBazaar، Myket و غیره
    environment = WTUserEnvironment.PRODUCTION, // یا SANDBOX برای آزمایش
    trackingWaitingTime = 5, // تأخیر به ثانیه قبل از شروع ردیابی
    startTrackerAutomatically = true, // شروع خودکار ردیابی
    oaidEnabled = true, // فعال‌سازی شناسه تبلیغاتی باز (نیازمند wisetrack-oaid)
    logLevel = WTLogLevel.DEBUG, // تنظیم سطح گزارش (DEBUG، INFO و غیره)
    referrerEnabled = true // فعال‌سازی ردیابی ارجاع (نیازمند wisetrack-referrer)
)
```

### مقداردهی اولیه SDK

SDK را در کلاس `Application` یا متد `onCreate` فعالیت اصلی مقداردهی کنید:

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

کلاس `Application` خود را در `AndroidManifest.xml` ثبت کنید:

```xml
<application
    android:name=".MyApplication"
    ...>
</application>
```

## استفاده

کلاس `WiseTrack` چندین متد عمومی برای تعامل با SDK ارائه می‌دهد. در ادامه راهنمای مفصلی برای هر متد ارائه شده است.

### مقداردهی اولیه

- `initialize(initialConfig: WTInitialConfig)`: SDK را با پیکربندی ارائه‌شده مقداردهی می‌کند.

  ```kotlin
  wiseTrack.initialize(config)
  ```

  - این متد را یک‌بار در زمان راه‌اندازی برنامه فراخوانی کنید.
  - اطمینان حاصل کنید که `initialConfig` شامل یک `appToken` معتبر است.

### شروع و توقف ردیابی

- `startTracking()`: ردیابی فعالیت‌ها و رویدادهای کاربر را شروع می‌کند.

  ```kotlin
  wiseTrack.startTracking()
  ```

  - اگر `startTrackerAutomatically` در `WTInitialConfig` نادرست باشد، این متد را فراخوانی کنید.
  - SDK تا 3 بار در صورت عدم مقداردهی، با تأخیر 3 ثانیه‌ای بین تلاش‌ها، دوباره تلاش می‌کند.

- `stopTracking()`: تمام عملیات ردیابی را متوقف می‌کند.

  ```kotlin
  wiseTrack.stopTracking()
  ```

  - از این متد برای توقف ردیابی استفاده کنید، مثلاً وقتی کاربر از تحلیل‌ها انصراف می‌دهد.

### ثبت رویدادها

- `logEvent(event: WTEvent)`: یک رویداد سفارشی مانند اقدامات کاربر یا تراکنش‌های درآمدی را ثبت می‌کند.

  ```kotlin
  import io.wisetrack.wisetrack_core.core.WTEvent
  import io.wisetrack.wisetrack_core.core.EventParam
  import io.wisetrack.wisetrack_core.core.RevenueCurrency

  // رویداد پیش‌فرض
  val defaultEvent = WTEvent.defaultEvent(
      name = "button_clicked",
      params = mapOf("button_id" to EventParam("start_button"))
  )
  wiseTrack.logEvent(defaultEvent)

  // رویداد درآمدی
  val revenueEvent = WTEvent.revenueEvent(
      name = "purchase",
      currency = RevenueCurrency.USD,
      amount = 9.99,
      params = mapOf("item" to EventParam("premium_subscription"))
  )
  wiseTrack.logEvent(revenueEvent)
  ```

  - از `WTEvent.defaultEvent` برای اقدامات کاربر یا رویدادهای سیستمی استفاده کنید.
  - از `WTEvent.revenueEvent` برای تراکنش‌های مالی استفاده کنید.

### مدیریت تنظیمات SDK

- `setEnabled(enabled: Boolean)`: SDK را فعال یا غیرفعال می‌کند.

  ```kotlin