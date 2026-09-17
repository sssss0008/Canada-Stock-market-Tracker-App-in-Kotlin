package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CanadianMarketData
import com.example.ui.components.TradingViewWidgetView
import com.example.widget.TradingViewHtmlBuilder

@Composable
fun StockHubScreen(
    currentSymbol: String,
    onSymbolSelected: (String) -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedAnalysisTab by remember { mutableIntStateOf(0) }
    var searchInput by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val stockChips = listOf(
        "TSX:SHOP" to "Shopify",
        "TSX:RY" to "Royal Bank",
        "TSX:TD" to "TD Bank",
        "TSX:ENB" to "Enbridge",
        "TSX:CNR" to "CN Rail",
        "TSX:CP" to "CPKC",
        "TSX:CSU" to "Constellation",
        "TSX:BAM" to "Brookfield",
        "TSX:SU" to "Suncor",
        "TSX:ABX" to "Barrick Gold",
        "TSX:ATD" to "Couche-Tard",
        "TSX:BMO" to "BMO"
    )

    val analysisTabs = listOf(
        "Details",
        "Technical",
        "Financials",
        "Profile"
    )

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
                // Symbol bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
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
                                imageVector = Icons.Default.Business,
                                contentDescription = "Stock Details",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = currentSymbol,
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
                                        text = "ANALYTICS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                }
                            }
                            Text(
                                text = "TSX Institutional Profile • Fundamentals, Charts & Financials",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(
                        onClick = { isSearchExpanded = !isSearchExpanded },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .testTag("hub_search_toggle")
                    ) {
                        Icon(
                            imageVector = if (isSearchExpanded) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Search Canadian Stock",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }

                if (isSearchExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .testTag("hub_symbol_input"),
                        placeholder = { Text("Search TSX Stock (e.g. SHOP, RY, TD, CSU)") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        trailingIcon = {
                            if (searchInput.isNotEmpty()) {
                                IconButton(onClick = {
                                    val formatted = CanadianMarketData.normalizeSymbol(searchInput)
                                    onSymbolSelected(formatted)
                                    focusManager.clearFocus()
                                    isSearchExpanded = false
                                }) {
                                    Icon(Icons.Default.QueryStats, contentDescription = "Apply", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (searchInput.isNotBlank()) {
                                val formatted = CanadianMarketData.normalizeSymbol(searchInput)
                                onSymbolSelected(formatted)
                                focusManager.clearFocus()
                                isSearchExpanded = false
                            }
                        }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }

                // Horizontal Stock Chip Selector
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    stockChips.forEach { (sym, label) ->
                        val isSelected = currentSymbol.equals(sym, ignoreCase = true)
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSymbolSelected(sym) },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = null,
                            modifier = Modifier.testTag("hub_chip_$label")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Sub-tabs: Details, Technical, Financials, Profile
                ScrollableTabRow(
                    selectedTabIndex = selectedAnalysisTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    edgePadding = 12.dp
                ) {
                    analysisTabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedAnalysisTab == index,
                            onClick = { selectedAnalysisTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedAnalysisTab == index) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            modifier = Modifier.testTag("hub_tab_$title")
                        )
                    }
                }
            }
        }

        // Widget display based on subtab
        val activeSymbol = if (currentSymbol.startsWith("FX:")) "TSX:SHOP" else currentSymbol

        when (selectedAnalysisTab) {
            0 -> {
                // Symbol Info
                val infoHtml = remember(activeSymbol, isDark) {
                    TradingViewHtmlBuilder.buildSymbolInfoHtml(activeSymbol, isDark)
                }
                TradingViewWidgetView(
                    htmlContent = infoHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "symbol_info_webview"
                )
            }
            1 -> {
                // Technical Analysis
                val techHtml = remember(activeSymbol, isDark) {
                    TradingViewHtmlBuilder.buildTechnicalAnalysisHtml(activeSymbol, isDark)
                }
                TradingViewWidgetView(
                    htmlContent = techHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "technical_analysis_webview"
                )
            }
            2 -> {
                // Financials
                val finHtml = remember(activeSymbol, isDark) {
                    TradingViewHtmlBuilder.buildFinancialsHtml(activeSymbol, isDark)
                }
                TradingViewWidgetView(
                    htmlContent = finHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "financials_webview"
                )
            }
            3 -> {
                // Company Profile
                val profileHtml = remember(activeSymbol, isDark) {
                    TradingViewHtmlBuilder.buildCompanyProfileHtml(activeSymbol, isDark)
                }
                TradingViewWidgetView(
                    htmlContent = profileHtml,
                    isDark = isDark,
                    modifier = Modifier.fillMaxSize(),
                    testTag = "profile_webview"
                )
            }
        }
    }
}
