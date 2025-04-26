package io.wisetrack.wisetrackapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wisetrack.sdk.core.utils.WTLogLevel
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date

data class LogInfo(
    val level: WTLogLevel,
    val message: String,
    val time: Date,
) {
    val levelColor: Color
        get() {
            return when (level) {
                WTLogLevel.DEBUG -> Color(0xFF2E3C48)
                WTLogLevel.INFO -> Color(0xFF2962FF)
                WTLogLevel.WARNING -> Color(0xFFFF6D00)
                WTLogLevel.ERROR -> Color(0xFFFF1744)
            }
        }
}

@Composable
fun LogsBottomSheet(logs: List<LogInfo>, logFlow: StateFlow<List<LogInfo>>) {
    val currentLogs = logFlow.collectAsState(initial = logs).value
    fun formatTime(date: Date): String {
        val formatter = SimpleDateFormat("HH:mm:ss")
        return formatter.format(date)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(16.dp)
    ) {
        Text("Logs", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(currentLogs) { _, log ->
                Column {
                    Row {
                        Text(
                            log.level.name.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.weight(1f),
                            color = log.levelColor,
                        )
                        Text(
                            formatTime(log.time),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                    Text(log.message, fontSize = 10.sp)
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            }
        }
    }
}