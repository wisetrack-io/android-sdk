package io.wisetrack.wisetrackapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wisetrack.sdk.core.WiseTrack
import io.wisetrack.sdk.core.core.EventParam
import io.wisetrack.sdk.core.core.RevenueCurrency
import io.wisetrack.sdk.core.core.WTEvent
import io.wisetrack.sdk.core.core.WTStoreName
import io.wisetrack.sdk.core.core.config.WTInitialConfig
import io.wisetrack.sdk.core.utils.WTLoggerOutput
import io.wisetrack.sdk.core.utils.WTLogLevel
import io.wisetrack.sdk.core.utils.WTLogger
import io.wisetrack.wisetrackapp.ui.theme.WiseTrackTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val isDarkTheme = remember { mutableStateOf(false) }
    WiseTrackTheme(darkTheme = isDarkTheme.value) {
        HomeScreen(
            onToggleTheme = { isDarkTheme.value = !isDarkTheme.value }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onToggleTheme: () -> Unit) {
    val scope = rememberCoroutineScope()
    val logs = remember { mutableStateListOf<LogInfo>() }
    val logFlow = remember { MutableStateFlow<List<LogInfo>>(emptyList()) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var logLevel by remember { mutableStateOf(WTLogLevel.DEBUG) }
    var appToken by remember { mutableStateOf("rMN5ZCwpOzY7") }
    var customStore by remember { mutableStateOf("") }
    var androidStore: WTStoreName? by remember { mutableStateOf(WTStoreName.Other) }
    var autoStartTracker by remember { mutableStateOf(true) }
    var trackingWaitingTime by remember { mutableStateOf(5) }
    var isInitialized by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }
    val wiseTrack = WiseTrack.getInstance(LocalContext.current)
    val initConfig = WTInitialConfig(appToken= appToken)

    LaunchedEffect(Unit) {
        WTLogger.getInstance().addOutput(object : WTLoggerOutput {
            override fun log(level: WTLogLevel, tag: String, message: String, throwable: Throwable?) {
                logs.add(LogInfo(level, message, Date()))
                logFlow.value = logs.toList()
            }
        })

//        logFlow.collect { newLogs ->
//            logs.clear()
//            logs.addAll(newLogs)
//        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.height(48.dp),
                title = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("WiseTrack App", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch { bottomSheetState.show() }
                    }) {
                        Icon(Icons.AutoMirrored.Default.List, contentDescription = "Logs")
                    }
                    IconButton(onClick = { wiseTrack.clearDataAndStop() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = onToggleTheme) {
                        Icon(Icons.Default.Face, contentDescription = "Toggle Theme")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    BottomButton(
                        isInitialized = isInitialized,
                        isStarted = isStarted,
                        autoStartTracker = autoStartTracker,
                        onInitialize = {
                            initConfig.appToken = appToken
                            initConfig.storeName = androidStore ?: WTStoreName.Custom(customStore)
                            initConfig.startTrackerAutomatically = autoStartTracker
                            initConfig.trackingWaitingTime = trackingWaitingTime
                            initConfig.logLevel = logLevel

                            wiseTrack.initialize(initConfig)
                            isInitialized = true
                            if (autoStartTracker) isStarted = true
                        },
                        onStart = {
                            wiseTrack.startTracking()
                            isStarted = true },
                        onStop = {
                            wiseTrack.stopTracking()
                            isStarted = false
                        }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))
            CustomDropdown(
                title = "🐞 Log Level",
                items = WTLogLevel.entries,
                selectedItem = logLevel,
                mapper = { it.name },
                onItemSelected = {
                    logLevel = it
                    initConfig.logLevel = it
                    wiseTrack.setLogLevel(it)
                }
            )
            CustomInputField(
                title = "🔑 App Token",
                value = appToken,
                onValueChange = { appToken = it },
                hint = "Enter App token"
            )
            CustomDropdown(
                title = "📬 Android Store",
                items = listOf(
                    WTStoreName.PlayStore,
                    WTStoreName.CafeBazaar,
                    WTStoreName.Myket,
                    WTStoreName.Other,
                    null
                ),
                mapper = { it?.value?.uppercase() ?: "custom" },
                selectedItem = androidStore,
                onItemSelected = { androidStore = it },
            )
            if (androidStore == null){
                CustomInputField(
                    title = "Custom Store",
                    value = customStore,
                    onValueChange = { customStore = it },
                    hint = "Enter Custom Store"
                )
            }
            CustomToggleSwitch(
                title = "Start Tracker Automatically",
                checked = autoStartTracker,
                onCheckedChange = { autoStartTracker = it }
            )
            if (autoStartTracker) {
                CustomInputField(
                    title = "⏳ Tracking Waiting Time",
                    value = trackingWaitingTime.toString(),
                    onValueChange = { trackingWaitingTime = it.toIntOrNull() ?: 0 },
                    hint = "Enter waiting time",
                    keyboardType = KeyboardType.Number
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlineButton(
                    text = "⚡️ Default Event",
                    onClick = {
                        wiseTrack.logEvent(WTEvent.defaultEvent(
                            name = "default-event-android",
                            params = mapOf(
                                "key-str" to EventParam("value1"),
                                "key-num" to EventParam(1.1),
                                "key-bool" to EventParam(true)
                            )
                        ))
                    },
                    modifier = Modifier.weight(1f)
                )
                OutlineButton(
                    text = "💵 Revenue Event",
                    onClick = {
                        wiseTrack.logEvent(WTEvent.revenueEvent(
                            name = "default-revenue-android",
                            amount = 120000.0,
                            currency = RevenueCurrency.RUB,
                            params = mapOf(
                                "key-str" to EventParam("value1"),
                                "key-num" to EventParam(1.1),
                                "key-bool" to EventParam(true)
                            )
                        ))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)){
                OutlineButton(
                    text = "📦 Set Packages Info",
                    onClick = {
                        wiseTrack.setPackagesInfo()
                    },
                    modifier = Modifier.weight(1f)
                )
                OutlineButton(
                    text = "🔈 Set FCM Token",
                    onClick = {
                        wiseTrack.setFCMToken("my-fcm-token")
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    if (bottomSheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { scope.launch { bottomSheetState.hide() } },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            LogsBottomSheet(logs = logs, logFlow = logFlow)
        }
    }
}