package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CanadianResource(
    val title: String,
    val category: String,
    val description: String,
    val officialUrl: String,
    val keyFeatures: List<String>
)

object CanadianMarketResources {
    val categories = listOf("All", "Regulatory & Filings", "Exchanges", "Tax & Accounts", "Market News")

    val list = listOf(
        CanadianResource(
            title = "SEDAR+ (System for Electronic Document Analysis and Retrieval)",
            category = "Regulatory & Filings",
            description = "The official Canadian securities database where all TSX and TSXV public corporations file financial statements, MD&A, prospectuses, and insider reports.",
            officialUrl = "https://www.sedarplus.ca",
            keyFeatures = listOf("Audited 10-K/Annual reports", "Quarterly earnings press releases", "Material change reports", "Management proxy circulars")
        ),
        CanadianResource(
            title = "SEDI (System for Electronic Disclosure by Insiders)",
            category = "Regulatory & Filings",
            description = "Canada's online service for the filing and viewing of insider trading reports as required by provincial securities regulators.",
            officialUrl = "https://www.sedi.ca",
            keyFeatures = listOf("CEO & CFO stock purchases", "Director option exercises", "Institutional block insider changes", "Real-time compliance tracking")
        ),
        CanadianResource(
            title = "TMX Money & TSX Official Group",
            category = "Exchanges",
            description = "The definitive hub for Toronto Stock Exchange (TSX) and TSX Venture Exchange (TSXV) official listings, real-time index changes, and company announcements.",
            officialUrl = "https://www.tmxmoney.com",
            keyFeatures = listOf("Official TSX Composite Index data", "TSX Venture resource filings", "Dividend declaration dates", "Corporate actions calendar")
        ),
        CanadianResource(
            title = "CRA (Canada Revenue Agency) Investment Guide",
            category = "Tax & Accounts",
            description = "Official taxation guidelines for Canadian retail investors covering TFSA contribution rules, RRSP deduction limits, eligible dividend tax credits, and capital gain inclusion.",
            officialUrl = "https://www.canada.ca/en/revenue-agency/services/tax/individuals/topics/about-your-tax-return/tax-return/completing-a-tax-return/personal-income/line-12700-capital-gains.html",
            keyFeatures = listOf("TFSA contribution tracking", "Eligible Dividend Gross-up rates", "Foreign withholding tax treaties", "Adjusted Cost Base (ACB) rules")
        ),
        CanadianResource(
            title = "Bank of Canada (Banque du Canada)",
            category = "Regulatory & Filings",
            description = "Canada's central bank responsible for monetary policy, inflation control (CPI), policy interest rates, and financial system stability reports.",
            officialUrl = "https://www.bankofcanada.ca",
            keyFeatures = listOf("Overnight policy rate decisions", "Monetary Policy Report (MPR)", "CAD/USD exchange rate fixings", "Canadian yield curve data")
        ),
        CanadianResource(
            title = "BNN Bloomberg Canada",
            category = "Market News",
            description = "Canada's premier business and financial news network delivering real-time Bay Street analysis, company interviews, and commodity market coverage.",
            officialUrl = "https://www.bnnbloomberg.ca",
            keyFeatures = listOf("Live Bay Street interviews", "Market Call stock recommendations", "Canadian energy & mining news", "Economic indicator analysis")
        )
    )
}

@Composable
fun ResourcesScreen(
    isDark: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredList = remember(selectedCategory) {
        if (selectedCategory == "All") CanadianMarketResources.list
        else CanadianMarketResources.list.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF090D16) else Color(0xFFF8FAFC))
            .testTag("resources_screen")
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
                    modifier = Modifier.testTag("resources_back_btn")
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
                        .background(Color(0xFF3B82F6).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CorporateFare,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Canadian Market Hub & Filings",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "SEDAR+, SEDI, TMX, CRA & Bank of Canada",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Category Filter
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(CanadianMarketResources.categories) { cat ->
                val isSelected = selectedCategory == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat,
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFDC2626),
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("res_chip_$cat")
                )
            }
        }

        // Resource Cards
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { resource ->
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF3B82F6).copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = resource.category,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF60A5FA),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resource.officialUrl))
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                    contentDescription = "Open Website",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = resource.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = resource.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Key Information Available:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        resource.keyFeatures.forEach { feat ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "•", color = Color(0xFFEF4444), fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = feat,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resource.officialUrl))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Visit Official Portal", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
