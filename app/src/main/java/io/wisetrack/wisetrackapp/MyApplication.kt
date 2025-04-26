package io.wisetrack.wisetrackapp

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        WiseTrack.getInstance(this, WTInitialConfig(appToken = "", startTrackerAutomatically = true, trackingWaitingTime = 5))
//            .initialize()

//        val config = WTInitialConfig(
//            appToken = "rMN5ZCwpOzY7",
//            startTrackerAutomatically = true,
//            trackingWaitingTime = 5,
//            oaidEnabled = true,
//            referrerEnabled = true,
//            logLevel = WTLogLevel.DEBUG
//        )
//        val wiseTrack = WiseTrack.getInstance(this, config)
//        wiseTrack.enableTestMode()
//        wiseTrack.initialize()
//        wiseTrack.setLogLevel(WTLogLevel.DEBUG)
    }
}