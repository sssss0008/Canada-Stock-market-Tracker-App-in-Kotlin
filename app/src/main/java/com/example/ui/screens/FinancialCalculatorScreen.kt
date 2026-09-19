package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

enum class CalculatorType(val title: String) {
    DIVIDEND("Dividend Yield"),
    DRIP_COMPOUND("DRIP Compound"),
    CAPITAL_GAINS("Capital Gains & Tax"),
    POSITION_SIZE("Position Sizing")
}

@Composable
fun FinancialCalculatorScreen(
    isDark: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCalc by remember { mutableStateOf(CalculatorType.DIVIDEND) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF090D16) else Color(0xFFF8FAFC))
            .testTag("financial_calculator_screen")
    ) {
        // Header
        Surface(
            color = if (isDark) Color(0xFF0F172A) else Color.White,
            tonalElevation = 3.dp,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("calculator_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "TSX Financial Calculators",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Dividends, DRIP, Capital Gains & Risk",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Calculator Selector Tabs
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(CalculatorType.values()) { calcType ->
                val isSelected = selectedCalc == calcType
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCalc = calcType },
                    label = {
                        Text(
                            text = calcType.title,
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFDC2626),
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("calc_tab_${calcType.name}")
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                when (selectedCalc) {
                    CalculatorType.DIVIDEND -> DividendCalculatorView(isDark)
                    CalculatorType.DRIP_COMPOUND -> DripCompoundCalculatorView(isDark)
                    CalculatorType.CAPITAL_GAINS -> CapitalGainsCalculatorView(isDark)
                    CalculatorType.POSITION_SIZE -> PositionSizeCalculatorView(isDark)
                }
            }
        }
    }
}

@Composable
fun DividendCalculatorView(isDark: Boolean) {
    var sharePriceText by remember { mutableStateOf("54.20") } // e.g. ENB or TD
    var annualDivText by remember { mutableStateOf("3.66") }
    var sharesCountText by remember { mutableStateOf("250") }

    val sharePrice = sharePriceText.toDoubleOrNull() ?: 0.0
    val annualDiv = annualDivText.toDoubleOrNull() ?: 0.0
    val shares = sharesCountText.toDoubleOrNull() ?: 0.0

    val yieldPercent = if (sharePrice > 0) (annualDiv / sharePrice) * 100 else 0.0
    val totalInvested = sharePrice * shares
    val annualIncome = annualDiv * shares
    val quarterlyIncome = annualIncome / 4
    val monthlyIncome = annualIncome / 12

    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.CANADA) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CalculatorInputCard(
            title = "Dividend Yield & Cash Flow",
            subtitle = "Calculate annual/monthly payouts from TSX dividend stocks (e.g. Enbridge, Royal Bank)",
            isDark = isDark
        ) {
            CalcTextField(
                label = "Share Price (\$ CAD)",
                value = sharePriceText,
                onValueChange = { sharePriceText = it },
                isDark = isDark,
                testTag = "input_share_price"
            )
            Spacer(modifier = Modifier.height(10.dp))
            CalcTextField(
                label = "Annual Dividend Per Share (\$ CAD)",
                value = annualDivText,
                onValueChange = { annualDivText = it },
                isDark = isDark,
                testTag = "input_annual_div"
            )
            Spacer(modifier = Modifier.height(10.dp))
            CalcTextField(
                label = "Number of Shares",
                value = sharesCountText,
                onValueChange = { sharesCountText = it },
                isDark = isDark,
                testTag = "input_shares_count"
            )
        }

        // Results Card
        Surface(
            color = if (isDark) Color(0xFF1E293B) else Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Dividend Yield & Income Summary",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Dividend Yield",
                        value = String.format(Locale.CANADA, "%.2f%%", yieldPercent),
                        highlightColor = Color(0xFF10B981)
                    )
                    MetricResultItem(
                        label = "Portfolio Value",
                        value = currencyFormat.format(totalInvested)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Annual Cashflow",
                        value = currencyFormat.format(annualIncome),
                        highlightColor = Color(0xFF38BDF8)
                    )
                    MetricResultItem(
                        label = "Quarterly Payment",
                        value = currencyFormat.format(quarterlyIncome)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                MetricResultItem(
                    label = "Monthly Average Income",
                    value = currencyFormat.format(monthlyIncome),
                    highlightColor = Color(0xFFFBBF24)
                )
            }
        }
    }
}

@Composable
fun DripCompoundCalculatorView(isDark: Boolean) {
    var initialInvestmentText by remember { mutableStateOf("10000") }
    var monthlyContributionText by remember { mutableStateOf("500") }
    var expectedReturnText by remember { mutableStateOf("8.5") }
    var yearsText by remember { mutableStateOf("15") }

    val initial = initialInvestmentText.toDoubleOrNull() ?: 0.0
    val monthly = monthlyContributionText.toDoubleOrNull() ?: 0.0
    val returnRate = (expectedReturnText.toDoubleOrNull() ?: 0.0) / 100.0
    val years = yearsText.toIntOrNull() ?: 0

    val months = years * 12
    val monthlyRate = returnRate / 12.0

    var futureValue = initial * (1 + monthlyRate).pow(months)
    if (monthlyRate > 0) {
        futureValue += monthly * ((1 + monthlyRate).pow(months) - 1) / monthlyRate
    } else {
        futureValue += monthly * months
    }

    val totalContributed = initial + (monthly * months)
    val totalInterest = (futureValue - totalContributed).coerceAtLeast(0.0)

    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.CANADA) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CalculatorInputCard(
            title = "DRIP & Wealth Compounding",
            subtitle = "Forecast long-term growth with automatic dividend reinvestment",
            isDark = isDark
        ) {
            CalcTextField(
                label = "Initial Investment (\$ CAD)",
                value = initialInvestmentText,
                onValueChange = { initialInvestmentText = it },
                isDark = isDark
            )
            Spacer(modifier = Modifier.height(10.dp))
            CalcTextField(
                label = "Monthly Contribution (\$ CAD)",
                value = monthlyContributionText,
                onValueChange = { monthlyContributionText = it },
                isDark = isDark
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Annual Return (%)",
                        value = expectedReturnText,
                        onValueChange = { expectedReturnText = it },
                        isDark = isDark
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Years to Invest",
                        value = yearsText,
                        onValueChange = { yearsText = it },
                        isDark = isDark
                    )
                }
            }
        }

        // Result
        Surface(
            color = if (isDark) Color(0xFF1E293B) else Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Projected Future Portfolio Value",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8)
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = currencyFormat.format(futureValue),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF38BDF8)
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Total Contributions",
                        value = currencyFormat.format(totalContributed)
                    )
                    MetricResultItem(
                        label = "Compound Growth",
                        value = currencyFormat.format(totalInterest),
                        highlightColor = Color(0xFF10B981)
                    )
                }
            }
        }
    }
}

@Composable
fun CapitalGainsCalculatorView(isDark: Boolean) {
    var purchasePriceText by remember { mutableStateOf("60.00") }
    var sellPriceText by remember { mutableStateOf("115.00") }
    var shareCountText by remember { mutableStateOf("200") }
    var marginalTaxRateText by remember { mutableStateOf("43.0") } // Average Canadian bracket

    val purchase = purchasePriceText.toDoubleOrNull() ?: 0.0
    val sell = sellPriceText.toDoubleOrNull() ?: 0.0
    val shares = shareCountText.toDoubleOrNull() ?: 0.0
    val taxRate = (marginalTaxRateText.toDoubleOrNull() ?: 0.0) / 100.0

    val totalCost = purchase * shares
    val totalProceeds = sell * shares
    val totalGain = totalProceeds - totalCost

    // Canadian 50% inclusion rule up to $250k
    val inclusionRate = 0.50
    val taxableGain = (totalGain * inclusionRate).coerceAtLeast(0.0)
    val estimatedTax = taxableGain * taxRate
    val netAfterTaxGain = totalGain - estimatedTax

    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.CANADA) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CalculatorInputCard(
            title = "Canadian Capital Gains & Tax Estimator",
            subtitle = "Calculates CRA capital gain inclusion (50%) & net after-tax return in non-registered accounts",
            isDark = isDark
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Purchase Price (\$)",
                        value = purchasePriceText,
                        onValueChange = { purchasePriceText = it },
                        isDark = isDark
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Sale Price (\$)",
                        value = sellPriceText,
                        onValueChange = { sellPriceText = it },
                        isDark = isDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Shares Sold",
                        value = shareCountText,
                        onValueChange = { shareCountText = it },
                        isDark = isDark
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Tax Bracket (%)",
                        value = marginalTaxRateText,
                        onValueChange = { marginalTaxRateText = it },
                        isDark = isDark
                    )
                }
            }
        }

        // Result Card
        Surface(
            color = if (isDark) Color(0xFF1E293B) else Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626).copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tax & Capital Gain Breakdown",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4444)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Gross Capital Gain",
                        value = currencyFormat.format(totalGain),
                        highlightColor = if (totalGain >= 0) Color(0xFF10B981) else Color(0xFFEF4444)
                    )
                    MetricResultItem(
                        label = "CRA Taxable (50%)",
                        value = currencyFormat.format(taxableGain)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Estimated CRA Tax",
                        value = currencyFormat.format(estimatedTax),
                        highlightColor = Color(0xFFF87171)
                    )
                    MetricResultItem(
                        label = "Net Keep Profit",
                        value = currencyFormat.format(netAfterTaxGain),
                        highlightColor = Color(0xFF34D399)
                    )
                }
            }
        }
    }
}

@Composable
fun PositionSizeCalculatorView(isDark: Boolean) {
    var accountSizeText by remember { mutableStateOf("50000") }
    var riskPercentText by remember { mutableStateOf("1.5") }
    var entryPriceText by remember { mutableStateOf("45.00") }
    var stopLossPriceText by remember { mutableStateOf("42.50") }

    val accountSize = accountSizeText.toDoubleOrNull() ?: 0.0
    val riskPercent = (riskPercentText.toDoubleOrNull() ?: 0.0) / 100.0
    val entryPrice = entryPriceText.toDoubleOrNull() ?: 0.0
    val stopPrice = stopLossPriceText.toDoubleOrNull() ?: 0.0

    val maxDollarRisk = accountSize * riskPercent
    val riskPerShare = (entryPrice - stopPrice).coerceAtLeast(0.01)
    val maxShares = if (riskPerShare > 0) (maxDollarRisk / riskPerShare).toInt() else 0
    val totalCapitalRequired = maxShares * entryPrice

    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.CANADA) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CalculatorInputCard(
            title = "Trade Risk & Position Sizing",
            subtitle = "Determine exact share quantity to never exceed your personal risk tolerance",
            isDark = isDark
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Account Balance (\$)",
                        value = accountSizeText,
                        onValueChange = { accountSizeText = it },
                        isDark = isDark
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Risk Per Trade (%)",
                        value = riskPercentText,
                        onValueChange = { riskPercentText = it },
                        isDark = isDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Entry Price (\$)",
                        value = entryPriceText,
                        onValueChange = { entryPriceText = it },
                        isDark = isDark
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CalcTextField(
                        label = "Stop Loss (\$)",
                        value = stopLossPriceText,
                        onValueChange = { stopLossPriceText = it },
                        isDark = isDark
                    )
                }
            }
        }

        // Result Card
        Surface(
            color = if (isDark) Color(0xFF1E293B) else Color.White,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Recommended Position",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59E0B)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Max Shares to Buy",
                        value = "$maxShares shares",
                        highlightColor = Color(0xFFF59E0B)
                    )
                    MetricResultItem(
                        label = "Max Capital Allocation",
                        value = currencyFormat.format(totalCapitalRequired)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricResultItem(
                        label = "Max Loss at Stop",
                        value = currencyFormat.format(maxDollarRisk),
                        highlightColor = Color(0xFFEF4444)
                    )
                    MetricResultItem(
                        label = "Risk Per Share",
                        value = currencyFormat.format(riskPerShare)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorInputCard(
    title: String,
    subtitle: String,
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    Surface(
        color = if (isDark) Color(0xFF1E293B) else Color.White,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.08f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.5.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
fun CalcTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDark: Boolean,
    testTag: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9),
            unfocusedContainerColor = if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
fun MetricResultItem(
    label: String,
    value: String,
    highlightColor: Color = Color.Unspecified
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            ),
            color = if (highlightColor != Color.Unspecified) highlightColor else MaterialTheme.colorScheme.onSurface
        )
    }
}
