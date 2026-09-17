package com.example.model

data class CanadianStock(
    val symbol: String,
    val name: String,
    val sector: String,
    val description: String = ""
)

object CanadianMarketData {
    val featuredStocks = listOf(
        CanadianStock("TSX:OSPTX", "S&P/TSX Composite", "Index", "Canada benchmark stock market index"),
        CanadianStock("TSX:SHOP", "Shopify Inc.", "Technology", "Global commerce platform headquartered in Ottawa"),
        CanadianStock("TSX:RY", "Royal Bank of Canada", "Financials", "Largest bank in Canada by market capitalization"),
        CanadianStock("TSX:TD", "Toronto-Dominion Bank", "Financials", "Major multinational banking and financial services"),
        CanadianStock("TSX:ENB", "Enbridge Inc.", "Energy", "North American energy infrastructure leader"),
        CanadianStock("TSX:CNR", "Canadian National Railway", "Industrials", "Transcontinental freight railway network"),
        CanadianStock("TSX:CP", "CPKC Railway", "Industrials", "Transnational railway network linking Canada, US, Mexico"),
        CanadianStock("TSX:CSU", "Constellation Software", "Technology", "Mission-critical vertical market software acquirer"),
        CanadianStock("TSX:BAM", "Brookfield Asset Mgmt", "Financials", "Alternative asset management global giant"),
        CanadianStock("TSX:BMO", "Bank of Montreal", "Financials", "Oldest bank in Canada with diversified operations"),
        CanadianStock("TSX:BNS", "Bank of Nova Scotia", "Financials", "International banking footprint across the Americas"),
        CanadianStock("TSX:SU", "Suncor Energy", "Energy", "Integrated energy company focusing on Canadian oil sands"),
        CanadianStock("TSX:TRI", "Thomson Reuters", "Technology", "Business information, legal & tax intelligence"),
        CanadianStock("TSX:ABX", "Barrick Gold Corp", "Materials", "One of the world's largest gold mining companies"),
        CanadianStock("TSX:ATD", "Couche-Tard Inc.", "Consumer Staples", "Global convenience store and fuel retail operator"),
        CanadianStock("TSX:MFC", "Manulife Financial", "Financials", "Leading international financial services group"),
        CanadianStock("TSX:NTR", "Nutrien Ltd.", "Materials", "World's largest provider of crop inputs and potash"),
        CanadianStock("TSX:BCE", "BCE Inc. (Bell)", "Telecom", "Canada's largest communications company"),
        CanadianStock("FX:USDCAD", "USD / CAD", "Forex", "US Dollar to Canadian Dollar currency pair")
    )

    fun normalizeSymbol(input: String): String {
        val trimmed = input.trim().uppercase()
        if (trimmed.isEmpty()) return "TSX:SHOP"
        return if (trimmed.contains(":")) {
            trimmed
        } else {
            // Default to TSX exchange if not specified
            "TSX:$trimmed"
        }
    }

    /**
     * Extracts a TSX or ticker symbol from TradingView URLs or link strings.
     * Examples:
     * - "https://www.tradingview.com/symbols/TSX-SHOP/" -> "TSX:SHOP"
     * - "https://www.tradingview.com/symbols/TSX-RY/" -> "TSX:RY"
     * - "https://www.tradingview.com/chart/?symbol=TSX%3ATD" -> "TSX:TD"
     * - "TSX:SHOP" -> "TSX:SHOP"
     * - "SHOP" -> "TSX:SHOP"
     */
    fun extractSymbolFromUrl(raw: String): String? {
        if (raw.isBlank()) return null
        val decoded = try {
            java.net.URLDecoder.decode(raw, "UTF-8")
        } catch (_: Exception) {
            raw
        }

        // Pattern 1: /symbols/EXCHANGE-TICKER or /symbols/TICKER
        val symbolsRegex = Regex("""/symbols/([A-Za-z0-9_]+)-([A-Za-z0-9_.]+)""", RegexOption.IGNORE_CASE)
        symbolsRegex.find(decoded)?.let { match ->
            val exchange = match.groupValues[1].uppercase()
            val ticker = match.groupValues[2].uppercase()
            return "$exchange:$ticker"
        }

        val singleSymbolRegex = Regex("""/symbols/([A-Za-z0-9_.]+)""", RegexOption.IGNORE_CASE)
        singleSymbolRegex.find(decoded)?.let { match ->
            val ticker = match.groupValues[1].uppercase()
            return if (ticker.contains(":")) ticker else "TSX:$ticker"
        }

        // Pattern 2: symbol=EXCHANGE:TICKER or symbol=TICKER
        val queryRegex = Regex("""[?&]symbol=([^&#]+)""", RegexOption.IGNORE_CASE)
        queryRegex.find(decoded)?.let { match ->
            val sym = match.groupValues[1].trim().uppercase()
            return if (sym.contains(":")) sym else "TSX:$sym"
        }

        // If it's already a symbol like "TSX:SHOP" or "SHOP"
        if (!decoded.startsWith("http://") && !decoded.startsWith("https://")) {
            return normalizeSymbol(decoded)
        }

        return null
    }
}
