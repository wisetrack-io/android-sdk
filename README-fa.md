# مستندات SDK WiseTrack برای اندروید

SDK WiseTrack یک ابزار تحلیل قدرتمند برای ردیابی تعاملات کاربران، رویدادها و معیارهای برنامه در اپلیکیشن‌های اندرویدی است. این ابزار رابط کاربری ساده‌ای برای راه‌اندازی ردیابی، ثبت رویدادها، مدیریت تنظیمات SDK و دریافت داده‌های تحلیلی مانند شناسه تبلیغاتی (ADID) و اطلاعات ارجاع (Referrer) ارائه می‌دهد.

## فهرست مطالب

- [ویژگی‌ها](#ویژگیها)
- [مجوزها](#مجوزها)
- [پیش‌نیازها](#پیش‌نیازها)
- [نصب](#نصب)
- [وابستگی‌ها برای ویژگی‌ها](#وابستگی‌ها-برای-ویژگی‌ها)
- [راه‌اندازی](#راه‌اندازی)
- [استفاده](#استفاده)
  - [راه‌اندازی اولیه](#راه‌اندازی-اولیه)
  - [شروع و توقف ردیابی](#شروع-و-توقف-ردیابی)
  - [ثبت رویدادها](#ثبت-رویدادها)
  - [مدیریت تنظیمات SDK](#مدیریت-تنظیمات-SDK)
  - [دریافت داده‌های تحلیلی](#دریافت-داده‌های-تحلیلی)
  - [تنظیم توکن FCM](#تنظیم-توکن-FCM)
  - [پاک‌سازی](#پاک‌سازی)
- [مثال](#مثال)
- [عیب‌یابی](#عیب‌یابی)
- [لایسنس](#لایسنس)

## ویژگی‌ها

- ردیابی تعاملات کاربران و رویدادهای سفارشی (مانند اقدامات کاربر، رویدادهای درآمدی).
- پشتیبانی از فروشگاه‌های مختلف (مانند گوگل پلی، کافه‌بازار، مایکت).
- محیط‌های قابل تنظیم (Sandbox، Production) و تنظیمات SDK.
- دریافت شناسه تبلیغاتی (ADID) و اطلاعات ارجاع.
- ادغام با Firebase Installation برای دریافت شناسه نصب فایربیس.
- پشتیبانی از شناسه تبلیغاتی باز (OAID) و شناسه تبلیغات هواوی.
- ردیابی ارجاع برای گوگل پلی و کافه‌بازار.
- ثبت لاگ‌های قوی با سطوح لاگ قابل تنظیم.
- ردیابی خودکار با تأخیرهای قابل تنظیم.

## پیش‌نیازها

- **سطح API اندروید**: 21 (لالی‌پاپ) یا بالاتر
- **کاتلین**: نسخه 1.9.0 یا بالاتر
- **وابستگی‌ها**: بخش [وابستگی‌ها برای ویژگی‌ها](#وابستگی‌ها-برای-ویژگی‌ها) را برای وابستگی‌های موردنیاز و اختیاری بررسی کنید.

## مجوزها

برای دسترسی SDK WiseTrack به اطلاعات دستگاه، باید مجوزهای موردنیاز اپلیکیشن خود را مشخص کنید. این مجوزها را در فایل `AndroidManifest.xml` اضافه کنید:

- برای دسترسی به قابلیت‌های آنلاین، مجوزهای زیر را اضافه کنید:

  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  ```

- اگر اپلیکیشن شما فروشگاه گوگل پلی را هدف قرار نمی‌دهد، برای دسترسی به وضعیت شبکه دستگاه، مجوزهای زیر را اضافه کنید:

  ```xml
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  ```

## نصب

1. **اضافه کردن SDK WiseTrack به پروژه**:

   - وابستگی را به فایل `app/build.gradle` اضافه کنید:

     ```gradle
     implementation 'io.wisetrack:sdk:core:2.0.0' // با آخرین نسخه جایگزین کنید
     ```

   - پروژه خود را با Gradle همگام‌سازی کنید.

2. **اضافه کردن وابستگی‌های خاص ویژگی‌ها**: بسته به ویژگی‌هایی که می‌خواهید فعال کنید (مانند OAID، ردیابی ارجاع)، وابستگی‌های مربوطه را طبق بخش [وابستگی‌ها برای ویژگی‌ها](#وابستگی‌ها-برای-ویژگی‌ها) اضافه کنید.

## وابستگی‌ها برای ویژگی‌ها

SDK WiseTrack به‌طور خودکار از ویژگی‌های خاصی استفاده می‌کند، اگر وابستگی‌های مربوطه در پروژه شما موجود باشند. در ادامه فهرستی از ویژگی‌ها و وابستگی‌های موردنیاز آورده شده است:

- **پل ارتباطی وب ویو**: در صورت استفاده از `WebView` در اپلیکیشن شما این قابلیت پشتیبانی میشود
  ```gradle
  implementation 'io.wisetrack:sdk:webbridge:2.0.0' // با آخرین نسخه جایگزین کنید
  ```

- **شناسه تبلیغاتی باز (OAID)**: فعال‌سازی `oaidEnabled` در `WTInitialConfig` برای استفاده از OAID به‌عنوان جایگزین ADID در گوشی‌های چینی بدون سرویس گوگل پلی.
  ```gradle
  implementation 'io.wisetrack:sdk:oaid:2.0.0' // با آخرین نسخه جایگزین کنید
  ```

- **ردیابی ارجاع**: فعال‌سازی `referrerEnabled` در `WTInitialConfig` و `getReferrer()` برای ردیابی منابع نصب.
  - برای بررسی آخرین نسخه ارجاع گوگل پلی، [اینجا](https://developer.android.com/google/play/installreferrer/library#dependenciess) را ببینید.
  - برای بررسی آخرین نسخه ارجاع کافه‌بازار، [اینجا](https://github.com/cafebazaar/ReferrerSDK/releases) را ببینید.
  ```gradle
  implementation 'io.wisetrack:sdk:referrer:2.0.0' // با آخرین نسخه جایگزین کنید
  implementation 'com.android.installreferrer:installreferrer:2.2' // برای ارجاع گوگل پلی
  implementation 'com.github.cafebazaar:referrersdk:1.0.2' // برای ارجاع کافه‌بازار
  ```

- **شناسه تبلیغاتی گوگل (ADID)**: فعال‌سازی `getADID()` برای دریافت شناسه تبلیغاتی گوگل.
  ```gradle
  implementation 'com.google.android.gms:play-services-ads-identifier:18.2.0'
  ```

- **شناسه تبلیغات هواوی**: فعال‌سازی دریافت ADID در دستگاه‌های هواوی.
  ```gradle
  implementation 'com.huawei.hms:ads-identifier:3.4.62.300'
  ```

- **شناسه نصب فایربیس (FID)**: فعال‌سازی شناسه نصب فایربیس برای شناسایی یکتا دستگاه کاربر.
  ```gradle
  implementation 'com.google.firebase:firebase-installations:17.2.0'
  ```

- **شناسه AppSet**: ارائه شناسایی اضافی دستگاه برای تحلیل.
  ```gradle
  implementation 'com.google.android.gms:play-services-appset:16.1.0'
  ```

**توجه**: فقط وابستگی‌های مربوط به ویژگی‌های موردنیاز خود را اضافه کنید. به‌عنوان مثال، اگر از دستگاه‌های هواوی استفاده نمی‌کنید، می‌توانید `huawei-hms-ads-identifier` را نادیده بگیرید. برای تنظیم این ویژگی‌ها در `WTInitialConfig`، بخش [راه‌اندازی](#راه‌اندازی) را بررسی کنید.

## راه‌اندازی

قبل از استفاده از SDK WiseTrack، باید آن را با یک شیء پیکربندی (`WTInitialConfig`) راه‌اندازی کنید. این کار معمولاً در کلاس `Application` یا `Activity` اصلی انجام می‌شود.

### ایجاد WTInitialConfig

شیء `WTInitialConfig` رفتار SDK را تعریف می‌کند، از جمله توکن اپلیکیشن، نام فروشگاه، محیط و تنظیمات ردیابی.

```kotlin
import io.wisetrack.sdk.core.core.WTInitialConfig
import io.wisetrack.sdk.core.core.WTStoreName
import io.wisetrack.sdk.core.core.WTUserEnvironment
import io.wisetrack.sdk.core.models.WTLogLevel

val config = WTInitialConfig(
    appToken = "YOUR_APP_TOKEN", // ارائه‌شده توسط WiseTrack
    storeName = WTStoreName.PlayStore, // یا CafeBazaar، Myket و غیره
    environment = WTUserEnvironment.PRODUCTION, // یا SANDBOX برای آزمایش
    trackingWaitingTime = 5, // تأخیر به ثانیه قبل از شروع ردیابی
    startTrackerAutomatically = true, // شروع خودکار ردیابی
    oaidEnabled = true, // فعال‌سازی شناسه تبلیغاتی باز (نیازمند wisetrack-oaid)
    logLevel = WTLogLevel.DEBUG, // تنظیم سطح لاگ (DEBUG، INFO و غیره)
    referrerEnabled = true // فعال‌سازی ردیابی ارجاع (نیازمند wisetrack-referrer)
)
```

### راه‌اندازی SDK

SDK را در کلاس `Application` یا در متد `onCreate` از `Activity` اصلی راه‌اندازی کنید:

```kotlin
import io.wisetrack.sdk.core.WiseTrack

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

کلاس `WiseTrack` چندین متد عمومی برای تعامل با SDK ارائه می‌دهد. در ادامه راهنمای مفصلی برای هر متد آورده شده است.

### راه‌اندازی اولیه

- `initialize(initialConfig: WTInitialConfig)`: SDK را با پیکربندی ارائه‌شده راه‌اندازی می‌کند.

  ```kotlin
  wiseTrack.initialize(config)
  ```

  - این متد را یک‌بار در زمان راه‌اندازی اپلیکیشن فراخوانی کنید.
  - اطمینان حاصل کنید که `initialConfig` شامل یک `appToken` معتبر است.

### شروع و توقف ردیابی

- `startTracking()`: ردیابی فعالیت‌ها و رویدادهای کاربر را آغاز می‌کند.

  ```kotlin
  wiseTrack.startTracking()
  ```

  - اگر `startTrackerAutomatically` در `WTInitialConfig` false باشد، این متد را فراخوانی کنید.
  - SDK در صورت عدم راه‌اندازی، تا 3 بار با تأخیر 3 ثانیه‌ای تلاش مجدد می‌کند.

- `stopTracking()`: تمام عملیات ردیابی را متوقف می‌کند.

  ```kotlin
  wiseTrack.stopTracking()
  ```

  - از این متد برای توقف ردیابی استفاده کنید، مثلاً زمانی که کاربر از تحلیل داده‌ها انصراف می‌دهد.

### ثبت رویدادها

- `logEvent(event: WTEvent)`: یک رویداد سفارشی مانند اقدامات کاربر یا تراکنش‌های درآمدی را ثبت می‌کند.

  ```kotlin
  import io.wisetrack.sdk.core.core.WTEvent
  import io.wisetrack.sdk.core.core.EventParam
  import io.wisetrack.sdk.core.core.RevenueCurrency

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
  wiseTrack.setEnabled(true) // فعال‌سازی SDK
  wiseTrack.setEnabled(false) // غیرفعال‌سازی SDK
  ```

  - اگر SDK در پیکربندی فعال نباشد یا نیاز به به‌روزرسانی اجباری داشته باشد، این متد نادیده گرفته می‌شود.

- `setLogLevel(level: WTLogLevel)`: سطح لاگ را برای لاگ‌های SDK تنظیم می‌کند.

  ```kotlin
  wiseTrack.setLogLevel(WTLogLevel.INFO)
  ```

  - گزینه‌ها: `DEBUG`، `INFO`، `WARNING`، `ERROR`، `NONE`.
  - در زمان توسعه از `DEBUG` و در محیط تولید از `INFO` یا بالاتر استفاده کنید.

- `clearDataAndStop()`: تمام داده‌های ذخیره‌شده را پاک کرده و ردیابی را متوقف می‌کند (برای آزمایش یا بازنشانی).

  ```kotlin
  wiseTrack.clearDataAndStop()
  ```

  - این متد را با احتیاط استفاده کنید، زیرا تمام داده‌های تحلیلی را حذف می‌کند.

### دریافت داده‌های تحلیلی

- `getADID(): String?`: شناسه تبلیغاتی (ADID) را در صورت موجود بودن دریافت می‌کند.

  ```kotlin
  val adid = wiseTrack.getADID()
  println("شناسه تبلیغاتی: $adid")
  ```

  - اگر ADID در دسترس نباشد، `null` برمی‌گرداند (نیازمند `play-services-ads-identifier` یا `huawei-hms-ads-identifier`).

- `getReferrer(): String?`: اطلاعات ارجاع را در صورت موجود بودن دریافت می‌کند.

  ```kotlin
  val referrer = wiseTrack.getReferrer()
  println("ارجاع: $referrer")
  ```

  - اگر ردیابی ارجاع غیرفعال یا در دسترس نباشد، `null` برمی‌گرداند (نیازمند `wisetrack-referrer`، `android-installreferrer` یا `cafebazaar-referrersdk`).

### تنظیم توکن FCM

- `setFCMToken(token: String)`: توکن Firebase Cloud Messaging (FCM) را برای اعلان‌های پوش تنظیم می‌کند.

  ```kotlin
  wiseTrack.setFCMToken("YOUR_FCM_TOKEN")
  ```

  - این متد را زمانی که توکن FCM دریافت یا به‌روزرسانی می‌شود، فراخوانی کنید.

### پاک‌سازی

- `destroy()`: منابع را پاک‌سازی کرده و ردیابی را متوقف می‌کند.

  ```kotlin
  wiseTrack.destroy()
  ```

  - این متد را زمانی که اپلیکیشن بسته می‌شود یا دیگر نیازی به SDK نیست، فراخوانی کنید.

## مثال

در زیر یک مثال کامل از نحوه استفاده از SDK WiseTrack در یک اپلیکیشن اندرویدی آورده شده است:

```kotlin
import android.app.Application
import io.wisetrack.sdk.core.WiseTrack
import io.wisetrack.sdk.core.core.WTInitialConfig
import io.wisetrack.sdk.core.core.WTStoreName
import io.wisetrack.sdk.core.core.WTUserEnvironment
import io.wisetrack.sdk.core.core.WTEvent
import io.wisetrack.sdk.core.core.EventParam
import io.wisetrack.sdk.core.models.WTLogLevel

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // راه‌اندازی WiseTrack
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

        // ثبت یک رویداد سفارشی
        val event = WTEvent.defaultEvent(
            name = "app_started",
            params = mapOf("version" to EventParam("1.0.0"))
        )
        wiseTrack.logEvent(event)

        // تنظیم توکن FCM
        wiseTrack.setFCMToken("YOUR_FCM_TOKEN")
    }
}
```

## عیب‌یابی

- **SDK راه‌اندازی نشده است**: اطمینان حاصل کنید که متد `initialize` با یک `WTInitialConfig` معتبر قبل از استفاده از سایر متدها فراخوانی شده است.
- **عدم دریافت ADID/Referrer**: بررسی کنید که وابستگی‌های موردنیاز (`play-services-ads-identifier`، `wisetrack-referrer` و غیره) اضافه شده‌اند و `oaidEnabled` یا `referrerEnabled` در `WTInitialConfig` تنظیم شده است. مجوزهای دستگاه را بررسی کنید.
- **لاگ‌ها قابل مشاهده نیستند**: سطح لاگ را به `DEBUG` یا `INFO` تنظیم کنید تا لاگ‌های دقیق‌تری مشاهده کنید.
- **ردیابی شروع نمی‌شود**: بررسی کنید که آیا `startTrackerAutomatically` false است و در صورت نیاز `startTracking` را به‌صورت دستی فراخوانی کنید.
- **خطاهای شبکه**: مطمئن شوید دستگاه به اینترنت متصل است و `WTUserEnvironment` در `WTInitialConfig` به‌درستی تنظیم شده است.

برای دریافت پشتیبانی بیشتر، با تیم پشتیبانی WiseTrack در آدرس support@wisetrack.io تماس بگیرید.

## لایسنس

SDK WiseTrack تحت لایسنس MIT منتشر شده است. برای جزئیات بیشتر، فایل LICENSE را مشاهده کنید.