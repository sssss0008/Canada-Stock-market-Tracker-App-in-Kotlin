package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ViewStream
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.ui.draw.scale
import com.example.ui.components.AnimatedMapleEmblem
import com.example.ui.components.AppNavigationDrawer
import com.example.ui.components.CandlestickChartVector
import com.example.ui.components.GlassButton
import com.example.ui.components.TradingViewWidgetView
import com.example.widget.TradingViewHtmlBuilder

@Composable
fun MainScreen(
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    onOpenOnboarding: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var currentSymbol by rememberSaveable { mutableStateOf("TSX:SHOP") }
    var showTickerTape by rememberSaveable { mutableStateOf(true) }
    var showAboutDialog by rememberSaveable { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var activeToolScreen by rememberSaveable { mutableStateOf<String?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                isDark = isDark,
                selectedTab = selectedTab,
                onSelectTab = { tabIndex ->
                    selectedTab = tabIndex
                    activeToolScreen = null
                },
                onOpenCalculator = {
                    activeToolScreen = "calculator"
                },
                onOpenDictionary = {
                    activeToolScreen = "dictionary"
                },
                onOpenResources = {
                    activeToolScreen = "resources"
                },
                onOpenAbout = {
                    showAboutDialog = true
                },
                onToggleTheme = onToggleTheme,
                onCloseDrawer = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.statusBarsPadding()) {
                        // Main Top App Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Drawer Hamburger Menu Button
                            GlassButton(
                                onClick = {
                                    coroutineScope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("open_drawer_btn"),
                                isDark = isDark,
                                accentColor = Color(0xFFDC2626)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Open Navigation Drawer",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.Center)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Canadian Flag Brand Emblem with animated institution glow
                            AnimatedMapleEmblem(
                                modifier = Modifier.padding(end = 4.dp),
                                size = 32.dp
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Canada Stock Screener",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        letterSpacing = (-0.2).sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                // Live TSX Badge with pulsating glowing dot
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(
                                                    Color(0xFFDC2626).copy(alpha = 0.22f),
                                                    Color(0xFF991B1B).copy(alpha = 0.15f)
                                                )
                                            )
                                        )
                                        .border(
                                            width = 1.dp,
                                            brush = Brush.horizontalGradient(
                                                listOf(
                                                    Color(0xFFEF4444).copy(alpha = 0.6f),
                                                    Color(0xFFDC2626).copy(alpha = 0.2f)
                                                )
                                            ),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    PulsingLiveDot()
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "TSX LIVE",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFFEF4444),
                                            letterSpacing = 0.7.sp
                                        )
                                    )
                                }
                            }
                            Text(
                                text = "Toronto Stock Exchange • Bay St",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Ticker Tape toggle button (Glassmorphic)
                        GlassButton(
                            onClick = { showTickerTape = !showTickerTape },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("toggle_ticker_tape"),
                            isDark = isDark,
                            accentColor = if (showTickerTape) Color(0xFFDC2626) else Color(0xFF64748B)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewStream,
                                contentDescription = "Toggle Ticker Tape",
                                tint = if (showTickerTape) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.Center)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Theme switch button (Glassmorphic)
                        GlassButton(
                            onClick = onToggleTheme,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("toggle_theme"),
                            isDark = isDark,
                            accentColor = Color(0xFFF59E0B)
                        ) {
                            Icon(
                                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Dark/Light Mode",
                                tint = if (isDark) Color(0xFFFBBF24) else Color(0xFF6366F1),
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.Center)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Info / About Us button (Glassmorphic)
                        GlassButton(
                            onClick = { showAboutDialog = true },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("open_about_dialog"),
                            isDark = isDark,
                            accentColor = Color(0xFF3B82F6)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "About Canada Stock Screener & Privacy",
                                tint = Color(0xFF60A5FA),
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.Center)
                            )
                        }

                    }

                    // Top Ticker Tape (Live Canadian TSX stream)
                    AnimatedVisibility(
                        visible = showTickerTape,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        val tickerTapeHtml = remember(isDark) {
                            TradingViewHtmlBuilder.buildTickerTapeHtml(isDark)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .background(if (isDark) Color(0xFF090D16) else Color(0xFFF8FAFC))
                                .testTag("main_ticker_tape")
                        ) {
                            TradingViewWidgetView(
                                htmlContent = tickerTapeHtml,
                                isDark = isDark,
                                modifier = Modifier.fillMaxSize(),
                                testTag = "ticker_tape_webview",
                                onSymbolSelected = { symbol ->
                                    currentSymbol = symbol
                                    selectedTab = 4
                                }
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Glassmorphic Floating Navigation Dock with custom micro-animations
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.linearGradient(
                            colors = if (isDark) listOf(
                                Color(0xFF1E293B).copy(alpha = 0.88f),
                                Color(0xFF0F172A).copy(alpha = 0.94f)
                            ) else listOf(
                                Color.White.copy(alpha = 0.94f),
                                Color(0xFFF1F5F9).copy(alpha = 0.90f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = if (isDark) listOf(
                                Color(0xFFDC2626).copy(alpha = 0.45f),
                                Color.White.copy(alpha = 0.12f),
                                Color(0xFF334155).copy(alpha = 0.2f)
                            ) else listOf(
                                Color(0xFFDC2626).copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0.8f),
                                Color(0xFFCBD5E1).copy(alpha = 0.4f)
                            )
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    val tabs = listOf(
                        Triple("Chart", Icons.Default.CandlestickChart, "nav_item_charts"),
                        Triple("Heatmap", Icons.Default.GridView, "nav_item_heatmap"),
                        Triple("Screener", Icons.Default.TravelExplore, "nav_item_screener"),
                        Triple("Calendar", Icons.Default.CalendarMonth, "nav_item_economy"),
                        Triple("Stock Hub", Icons.Default.Analytics, "nav_item_stock_hub")
                    )

                    tabs.forEachIndexed { index, (label, icon, testTag) ->
                        val isSelected = selectedTab == index && activeToolScreen == null
                        val iconScale by animateFloatAsState(
                            targetValue = if (isSelected) 1.15f else 1.0f,
                            animationSpec = spring(dampingRatio = 0.65f, stiffness = 400f),
                            label = "nav_icon_scale_$index"
                        )

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                selectedTab = index
                                activeToolScreen = null
                            },
                            icon = {
                                Box(
                                    modifier = Modifier.scale(iconScale),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (index == 0) {
                                        // Custom Financial Candlestick icon
                                        CandlestickChartVector(
                                            modifier = Modifier.size(22.dp),
                                            tint = if (isSelected) Color(0xFF10B981) else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    } else if (index == 4) {
                                        BadgedBox(
                                            badge = {
                                                Box(
                                                    modifier = Modifier
                                                        .size(7.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFDC2626))
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = label,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    } else {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = label,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectedTextColor = if (isDark) Color(0xFFF1F5F9) else Color(0xFF0F172A),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = Color(0xFFDC2626)
                            ),
                            modifier = Modifier.testTag(testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (activeToolScreen != null) {
                when (activeToolScreen) {
                    "calculator" -> FinancialCalculatorScreen(
                        isDark = isDark,
                        onBack = { activeToolScreen = null }
                    )
                    "dictionary" -> DictionaryScreen(
                        isDark = isDark,
                        onBack = { activeToolScreen = null }
                    )
                    "resources" -> ResourcesScreen(
                        isDark = isDark,
                        onBack = { activeToolScreen = null }
                    )
                }
            } else {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(180)) togetherWith fadeOut(animationSpec = tween(120))
                    },
                    label = "screen_tab_transition"
                ) { targetTab ->
                    when (targetTab) {
                        0 -> ChartScreen(
                            currentSymbol = currentSymbol,
                            onSymbolSelected = { currentSymbol = it },
                            isDark = isDark
                        )
                        1 -> HeatmapScreen(
                            isDark = isDark,
                            onStockSelected = { symbol ->
                                currentSymbol = symbol
                                selectedTab = 4
                            }
                        )
                        2 -> ScreenerScreen(
                            isDark = isDark,
                            onStockSelected = { symbol ->
                                currentSymbol = symbol
                                selectedTab = 4
                            }
                        )
                        3 -> EconomicScreen(
                            isDark = isDark
                        )
                        4 -> StockHubScreen(
                            currentSymbol = currentSymbol,
                            onSymbolSelected = { currentSymbol = it },
                            isDark = isDark
                        )
                    }
                }
            }
        }
    }
    }

    if (showAboutDialog) {
        AboutDialog(
            onDismiss = { showAboutDialog = false }
        )
    }
}

@Composable
private fun PulsingLiveDot(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    Box(
        modifier = modifier
            .size(5.dp)
            .clip(CircleShape)
            .background(Color(0xFFEF4444).copy(alpha = pulseAlpha))
    )
}
