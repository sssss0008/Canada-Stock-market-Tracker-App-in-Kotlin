package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val pageNumber: Int,
    val tag: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val features: List<String>,
    val badgeColor: Color
)

val onboardingPages = listOf(
    OnboardingPageData(
        pageNumber = 1,
        tag = "INTERACTIVE CHARTS",
        title = "Real-Time TSX Charts",
        subtitle = "Analyze Toronto Stock Exchange equities with institutional precision and customizable technical overlays.",
        icon = Icons.Default.CandlestickChart,
        features = listOf(
            "Default live chart focused on top Canadian leaders including Shopify (TSX:SHOP)",
            "Multi-timeframe views (1D, 5D, 1M, 1Y, 5Y, All) with full Candlestick & line options",
            "Advanced technical indicators: Moving Averages, RSI, MACD, and Bollinger Bands"
        ),
        badgeColor = Color(0xFF38BDF8)
    ),
    OnboardingPageData(
        pageNumber = 2,
        tag = "MARKET DISCOVERY",
        title = "Live Screener & Heatmap",
        subtitle = "Spot top gainers, high-yield dividend aristocrats, and sector rotation across the Canadian market.",
        icon = Icons.Default.GridView,
        features = listOf(
            "Comprehensive TSX stock screener filtered by Market Cap, P/E ratio, and Dividend Yield",
            "Real-time Sector Heatmaps for Financials, Energy, Technology, Industrials & Materials",
            "Interactive ticker tape streaming live Bay Street price updates at a glance"
        ),
        badgeColor = Color(0xFF10B981)
    ),
    OnboardingPageData(
        pageNumber = 3,
        tag = "MACRO & PRIVACY",
        title = "Economic Events & Privacy",
        subtitle = "Stay ahead of Bank of Canada interest rate decisions while enjoying a 100% private, free app.",
        icon = Icons.Default.CalendarMonth,
        features = listOf(
            "Macroeconomic calendar tracking Bank of Canada rate changes, CPI, GDP and job data",
            "Institutional Stock Hub with fundamentals, valuations, and deep financial breakdowns",
            "Zero Tracking Guarantee: We do not collect or track your personal data. 100% Free!"
        ),
        badgeColor = Color(0xFFDC2626)
    )
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0F19),
                        Color(0xFF111827),
                        Color(0xFF0F172A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 24.dp)
        ) {
            // Header Bar: App Name & Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🍁", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Canada Stock Screener",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                if (pagerState.currentPage < onboardingPages.size - 1) {
                    TextButton(
                        onClick = onFinish,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF94A3B8)
                        )
                    ) {
                        Text(
                            text = "Skip",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Pager with 3 Pages
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val pageData = onboardingPages[page]
                OnboardingPageContent(
                    data = pageData,
                    pageIndex = page
                )
            }

            // Bottom Navigation Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    repeat(onboardingPages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(6.dp)
                                .width(if (isSelected) 24.dp else 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (isSelected) Color(0xFFDC2626) else Color(0xFF334155)
                                )
                        )
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (pagerState.currentPage > 0) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFCBD5E1)
                            )
                        ) {
                            Text(
                                text = "Back",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (pagerState.currentPage < onboardingPages.size - 1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                onFinish()
                            }
                        },
                        modifier = Modifier
                            .weight(if (pagerState.currentPage > 0) 1.5f else 1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC2626)
                        )
                    ) {
                        Text(
                            text = if (pagerState.currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (pagerState.currentPage == onboardingPages.size - 1) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    data: OnboardingPageData,
    pageIndex: Int,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bespoke Visual Card for Each Page
        when (pageIndex) {
            0 -> OnboardingChartVisual()
            1 -> OnboardingScreenerVisual()
            else -> OnboardingMacroVisual()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tag Chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(data.badgeColor.copy(alpha = 0.15f))
                .border(1.dp, data.badgeColor.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = data.tag,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = data.badgeColor,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Title & Subtitle
        Text(
            text = data.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = data.subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF94A3B8),
                lineHeight = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Feature Highlight Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            data.features.forEach { featureText ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x221E293B)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33334155))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(data.badgeColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = data.badgeColor,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = featureText,
                            fontSize = 12.sp,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun OnboardingChartVisual() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF131C2E)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // Grid lines
                for (i in 1..4) {
                    val y = height * (i / 5f)
                    drawLine(
                        color = Color(0x15FFFFFF),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1f
                    )
                }

                // Candlesticks simulation
                val candlePoints = listOf(
                    Triple(0.15f, 0.65f, 0.45f),
                    Triple(0.28f, 0.55f, 0.40f),
                    Triple(0.40f, 0.48f, 0.58f),
                    Triple(0.53f, 0.50f, 0.32f),
                    Triple(0.66f, 0.38f, 0.26f),
                    Triple(0.80f, 0.30f, 0.18f),
                    Triple(0.92f, 0.22f, 0.12f)
                )

                candlePoints.forEach { (xPct, openPct, closePct) ->
                    val x = width * xPct
                    val openY = height * openPct
                    val closeY = height * closePct
                    val isGreen = closeY < openY
                    val color = if (isGreen) Color(0xFF10B981) else Color(0xFFEF4444)

                    // Wick
                    val highY = minOf(openY, closeY) - 14f
                    val lowY = maxOf(openY, closeY) + 14f
                    drawLine(
                        color = color,
                        start = Offset(x, highY),
                        end = Offset(x, lowY),
                        strokeWidth = 2f
                    )

                    // Body
                    val bodyTop = minOf(openY, closeY)
                    val bodyH = maxOf(Math.abs(closeY - openY), 8f)
                    drawRect(
                        color = color,
                        topLeft = Offset(x - 8f, bodyTop),
                        size = Size(16f, bodyH)
                    )
                }

                // Moving Average Path
                val maPath = Path().apply {
                    moveTo(width * 0.1f, height * 0.60f)
                    cubicTo(
                        width * 0.35f, height * 0.52f,
                        width * 0.65f, height * 0.35f,
                        width * 0.95f, height * 0.15f
                    )
                }
                drawPath(
                    path = maPath,
                    color = Color(0xFF38BDF8),
                    style = Stroke(width = 3f)
                )
            }

            // Overlay Symbol Badge
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(14.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xCC0F172A))
                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TSX:SHOP",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$124.80 CAD",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF38BDF8)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "+3.42%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981)
                )
            }
        }
    }
}

@Composable
fun OnboardingScreenerVisual() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF131C2E)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TSX SECTOR HEATMAP MATRIX",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "BAY STREET LIVE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF10B981)
                )
            }

            // Grid Tiles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Shopify tile
                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .height(88.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF065F46))
                        .border(1.dp, Color(0xFF10B981), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("SHOP", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    Text("Tech • Shopify", fontSize = 10.sp, color = Color(0xFFA7F3D0))
                    Text("+4.85%", fontWeight = FontWeight.ExtraBold, color = Color(0xFF6EE7B7), fontSize = 13.sp)
                }

                // Royal Bank tile
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .height(88.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF047857))
                        .border(1.dp, Color(0xFF059669), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("RY", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    Text("Banking", fontSize = 10.sp, color = Color(0xFFA7F3D0))
                    Text("+1.42%", fontWeight = FontWeight.Bold, color = Color(0xFF6EE7B7), fontSize = 13.sp)
                }

                // Enbridge tile
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(88.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF991B1B))
                        .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("ENB", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    Text("Energy", fontSize = 10.sp, color = Color(0xFFFECACA))
                    Text("-0.68%", fontWeight = FontWeight.Bold, color = Color(0xFFFCA5A5), fontSize = 13.sp)
                }
            }

            // Quick filter chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Top Gainers", "Div Yield > 4%", "Tech Leaders", "Mega Cap").forEach { filter ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x331E293B))
                            .border(0.5.dp, Color(0x55475569), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingMacroVisual() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF131C2E)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "BANK OF CANADA & TSX MACRO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF94A3B8),
                        letterSpacing = 1.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x3310B981))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("100% PRIVATE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                }
            }

            // Event 1: BoC Interest Rate
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x331E293B))
                    .border(1.dp, Color(0x33475569), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Bank of Canada Interest Rate Decision", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.White)
                    Text("Overnight Rate Target: 4.25% • High Impact", fontSize = 10.sp, color = Color(0xFF94A3B8))
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFDC2626))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("BOC", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Event 2: Canada CPI Inflation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x331E293B))
                    .border(1.dp, Color(0x33475569), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Statistics Canada CPI Inflation (YoY)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.White)
                    Text("Forecast: 2.5% • Prev: 2.7%", fontSize = 10.sp, color = Color(0xFF94A3B8))
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF2563EB))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("CPI", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
