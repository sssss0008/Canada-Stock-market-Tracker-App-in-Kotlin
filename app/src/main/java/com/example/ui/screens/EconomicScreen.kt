package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TradingViewWidgetView
import com.example.widget.TradingViewHtmlBuilder

@Composable
fun EconomicScreen(
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0 = Calendar, 1 = Economic Map

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            )
                            .border(
                                width = 0.8.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (selectedSubTab == 0) Icons.Default.CalendarMonth else Icons.Default.Public,
                            contentDescription = "Economic",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (selectedSubTab == 0) "Canada Economic Calendar" else "North America Economic Map",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = if (selectedSubTab == 0) "BoC & STATCAN" else "REGIONAL",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                        Text(
                            text = if (selectedSubTab == 0)
                                "Bank of Canada, CPI Inflation, Employment & GDP releases"
                            else
                                "Macroeconomic health & cross-border financial metrics",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sub-tabs
                TabRow(
                    selectedTabIndex = selectedSubTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = selectedSubTab == 0,
                        onClick = { selectedSubTab = 0 },
                        text = {
                            Text(
                                "Economic Events (BoC)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                        },
                        modifier = Modifier.testTag("econ_tab_calendar")
                    )
                    Tab(
                        selected = selectedSubTab == 1,
                        onClick = { selectedSubTab = 1 },
                        text = {
                            Text(
                                "Economic Regional Map",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                        },
                        modifier = Modifier.testTag("econ_tab_map")
                    )
                }
            }
        }

        // Widget display based on subtab
        when (selectedSubTab) {
            0 -> {
                val calendarHtml = remember(isDark) {
                    TradingViewHtmlBuilder.buildEconomicCalendarHtml(isDark = isDark, countryFilter = "ca")
                }
                TradingViewWidgetView(
                    htmlContent = calendarHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "calendar_webview"
                )
            }
            1 -> {
                val mapHtml = remember(isDark) {
                    TradingViewHtmlBuilder.buildEconomicMapHtml(isDark = isDark)
                }
                TradingViewWidgetView(
                    htmlContent = mapHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "economic_map_webview"
                )
            }
        }
    }
}
