package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CandlestickChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppNavigationDrawer(
    isDark: Boolean,
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    onOpenCalculator: () -> Unit,
    onOpenDictionary: () -> Unit,
    onOpenResources: () -> Unit,
    onOpenAbout: () -> Unit,
    onToggleTheme: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp),
        drawerContainerColor = if (isDark) Color(0xFF090D16) else Color(0xFFF8FAFC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with Canadian Emblem & Brand
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = if (isDark) listOf(
                                Color(0xFF1E293B),
                                Color(0xFF0F172A),
                                Color(0xFF7F1D1D)
                            ) else listOf(
                                Color(0xFFDC2626),
                                Color(0xFF991B1B)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedMapleEmblem(size = 40.dp)

                        IconButton(
                            onClick = onCloseDrawer,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.2f))
                                .testTag("drawer_close_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Drawer",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Canada Stock Screener",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Toronto Stock Exchange (TSX) & Financial Hub",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.5.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Core Tabs Section
            DrawerSectionHeader(title = "CORE SCREENS")

            DrawerMenuItem(
                icon = Icons.Default.CandlestickChart,
                title = "Interactive Charts",
                subtitle = "TSX Live Candlesticks & Technicals",
                isSelected = selectedTab == 0,
                accentColor = Color(0xFF10B981),
                isDark = isDark,
                onClick = {
                    onSelectTab(0)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.GridView,
                title = "Sector Heatmap",
                subtitle = "TSX 60 Market Cap & Momentum",
                isSelected = selectedTab == 1,
                accentColor = Color(0xFF3B82F6),
                isDark = isDark,
                onClick = {
                    onSelectTab(1)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.TravelExplore,
                title = "Stock Screener",
                subtitle = "Filter by Volume, Yield & P/E",
                isSelected = selectedTab == 2,
                accentColor = Color(0xFFF59E0B),
                isDark = isDark,
                onClick = {
                    onSelectTab(2)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.CalendarMonth,
                title = "Economic Calendar",
                subtitle = "Bank of Canada, CPI, Jobs & GDP",
                isSelected = selectedTab == 3,
                accentColor = Color(0xFF8B5CF6),
                isDark = isDark,
                onClick = {
                    onSelectTab(3)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Analytics,
                title = "Stock Hub",
                subtitle = "Fundamental profiles & TSX leaders",
                isSelected = selectedTab == 4,
                accentColor = Color(0xFFEC4899),
                isDark = isDark,
                onClick = {
                    onSelectTab(4)
                    onCloseDrawer()
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                color = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.08f)
            )

            // New Financial Tools & Dictionary Section
            DrawerSectionHeader(title = "FINANCIAL TOOLS & KNOWLEDGE")

            DrawerMenuItem(
                icon = Icons.Default.Calculate,
                title = "Financial Calculators",
                subtitle = "Dividends, DRIP, Capital Gains, Risk",
                isSelected = false,
                accentColor = Color(0xFF10B981),
                isDark = isDark,
                isNewFeature = true,
                onClick = {
                    onCloseDrawer()
                    onOpenCalculator()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.MenuBook,
                title = "Financial Dictionary",
                subtitle = "TFSA, RRSP, P/E, RSI, ACB terms",
                isSelected = false,
                accentColor = Color(0xFFDC2626),
                isDark = isDark,
                isNewFeature = true,
                onClick = {
                    onCloseDrawer()
                    onOpenDictionary()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Public,
                title = "Canadian Market Hub",
                subtitle = "SEDAR+, SEDI, TMX & Bank of Canada",
                isSelected = false,
                accentColor = Color(0xFF38BDF8),
                isDark = isDark,
                isNewFeature = true,
                onClick = {
                    onCloseDrawer()
                    onOpenResources()
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                color = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.08f)
            )

            // System & Information Section
            DrawerSectionHeader(title = "SYSTEM & PREFERENCES")

            DrawerMenuItem(
                icon = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                title = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                subtitle = "Adjust UI brightness and charts",
                isSelected = false,
                accentColor = Color(0xFFFBBF24),
                isDark = isDark,
                onClick = {
                    onToggleTheme()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Info,
                title = "About Us & Privacy",
                subtitle = "TSX disclaimers, methodology & terms",
                isSelected = false,
                accentColor = Color(0xFF64748B),
                isDark = isDark,
                onClick = {
                    onCloseDrawer()
                    onOpenAbout()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        ),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
    )
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    accentColor: Color,
    isDark: Boolean,
    onClick: () -> Unit,
    isNewFeature: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isSelected) {
            if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9)
        } else Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = if (isSelected) 0.25f else 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                            fontSize = 14.sp
                        ),
                        color = if (isSelected) {
                            if (isDark) Color.White else Color(0xFF0F172A)
                        } else MaterialTheme.colorScheme.onSurface
                    )
                    if (isNewFeature) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFDC2626))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "NEW",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
