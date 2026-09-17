package com.example.widget

object TradingViewHtmlBuilder {

    private fun getBgColor(isDark: Boolean): String = if (isDark) "#090D16" else "#F8FAFC"
    private fun getTheme(isDark: Boolean): String = if (isDark) "dark" else "light"

    fun buildTickerTapeHtml(isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: hidden;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-ticker-tape.js" async>
                {
                  "symbols": [
                    { "proName": "TSX:OSPTX", "title": "S&P/TSX Composite" },
                    { "proName": "TSX:SHOP", "title": "Shopify" },
                    { "proName": "TSX:RY", "title": "Royal Bank (RBC)" },
                    { "proName": "TSX:TD", "title": "TD Bank" },
                    { "proName": "TSX:ENB", "title": "Enbridge" },
                    { "proName": "TSX:CNR", "title": "CN Railway" },
                    { "proName": "TSX:CP", "title": "CPKC" },
                    { "proName": "TSX:CSU", "title": "Constellation" },
                    { "proName": "TSX:BAM", "title": "Brookfield" },
                    { "proName": "TSX:BMO", "title": "BMO" },
                    { "proName": "TSX:BNS", "title": "Scotiabank" },
                    { "proName": "TSX:SU", "title": "Suncor" },
                    { "proName": "TSX:TRI", "title": "Thomson Reuters" },
                    { "proName": "TSX:ABX", "title": "Barrick Gold" },
                    { "proName": "TSX:ATD", "title": "Couche-Tard" },
                    { "proName": "FX:USDCAD", "title": "USD / CAD" }
                  ],
                  "showSymbolLogo": true,
                  "isTransparent": false,
                  "displayMode": "adaptive",
                  "colorTheme": "$theme",
                  "locale": "en"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildAdvancedChartHtml(symbol: String, isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        val cleanSymbol = symbol.ifBlank { "TSX:SHOP" }
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: hidden;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container" style="height:100%;width:100%">
                <div class="tradingview-widget-container__widget" style="height:100%;width:100%"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js" async>
                {
                  "autosize": true,
                  "symbol": "$cleanSymbol",
                  "interval": "D",
                  "timezone": "America/Toronto",
                  "theme": "$theme",
                  "style": "1",
                  "locale": "en",
                  "allow_symbol_change": true,
                  "calendar": false,
                  "details": true,
                  "hide_side_toolbar": false,
                  "hide_top_toolbar": false,
                  "hide_legend": false,
                  "hide_volume": false,
                  "hotlist": true,
                  "save_image": true,
                  "backgroundColor": "$bg",
                  "gridColor": "rgba(46, 46, 46, 0.15)",
                  "watchlist": [
                    "TSX:OSPTX",
                    "TSX:SHOP",
                    "TSX:RY",
                    "TSX:TD",
                    "TSX:ENB",
                    "TSX:CNR",
                    "TSX:CP",
                    "TSX:CSU",
                    "TSX:BAM",
                    "TSX:BMO",
                    "TSX:BNS",
                    "TSX:SU",
                    "TSX:TRI",
                    "TSX:ABX",
                    "TSX:ATD",
                    "FX:USDCAD"
                  ],
                  "withdateranges": true,
                  "range": "YTD",
                  "compareSymbols": [],
                  "support_host": "https://www.tradingview.com"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildHeatmapHtml(isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: hidden;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-stock-heatmap.js" async>
                {
                  "dataSource": "ALLCA",
                  "blockSize": "market_cap_basic",
                  "blockColor": "change",
                  "grouping": "sector",
                  "locale": "en",
                  "colorTheme": "$theme",
                  "exchanges": [],
                  "hasTopBar": true,
                  "isDataSetEnabled": true,
                  "isZoomEnabled": true,
                  "hasSymbolTooltip": true,
                  "isMonoSize": false,
                  "width": "100%",
                  "height": "100%"
                }
                </script>
              </div>
              <script>
                // Intercept anchor clicks and window navigation to redirect to Stock Hub in app
                document.addEventListener('click', function(e) {
                  var target = e.target;
                  while (target && target.tagName !== 'A') {
                    target = target.parentElement;
                  }
                  if (target && target.href) {
                    var href = target.href;
                    if (window.AndroidBridge && window.AndroidBridge.onSymbolClicked) {
                      window.AndroidBridge.onSymbolClicked(href);
                      e.preventDefault();
                      e.stopPropagation();
                      return false;
                    }
                  }
                }, true);
              </script>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildScreenerHtml(isDark: Boolean, defaultScreen: String = "most_capitalized"): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-screener.js" async>
                {
                  "market": "canada",
                  "showToolbar": true,
                  "defaultColumn": "overview",
                  "defaultScreen": "$defaultScreen",
                  "isTransparent": false,
                  "locale": "en",
                  "colorTheme": "$theme",
                  "width": "100%",
                  "height": "100%"
                }
                </script>
              </div>
              <script>
                // Intercept anchor clicks and window navigation to redirect to Stock Hub in app
                document.addEventListener('click', function(e) {
                  var target = e.target;
                  while (target && target.tagName !== 'A') {
                    target = target.parentElement;
                  }
                  if (target && target.href) {
                    var href = target.href;
                    if (window.AndroidBridge && window.AndroidBridge.onSymbolClicked) {
                      window.AndroidBridge.onSymbolClicked(href);
                      e.preventDefault();
                      e.stopPropagation();
                      return false;
                    }
                  }
                }, true);
              </script>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildEconomicCalendarHtml(isDark: Boolean, countryFilter: String = "ca"): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-events.js" async>
                {
                  "colorTheme": "$theme",
                  "isTransparent": false,
                  "locale": "en",
                  "countryFilter": "$countryFilter",
                  "importanceFilter": "-1,0,1",
                  "width": "100%",
                  "height": "100%"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildEconomicMapHtml(isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: hidden;
                  background-color: $bg;
                }
                tv-economic-map {
                  width: 100%;
                  height: 100%;
                  display: block;
                }
              </style>
              <script type="module" src="https://widgets.tradingview-widget.com/w/en/tv-economic-map.js"></script>
            </head>
            <body>
              <tv-economic-map region="north-america" color-theme="$theme" hide-legend></tv-economic-map>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildSymbolInfoHtml(symbol: String, isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        val cleanSymbol = symbol.ifBlank { "TSX:SHOP" }
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-symbol-info.js" async>
                {
                  "symbol": "$cleanSymbol",
                  "colorTheme": "$theme",
                  "isTransparent": false,
                  "locale": "en",
                  "width": "100%"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildTechnicalAnalysisHtml(symbol: String, isDark: Boolean, interval: String = "1D"): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        val cleanSymbol = symbol.ifBlank { "TSX:SHOP" }
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-technical-analysis.js" async>
                {
                  "interval": "$interval",
                  "width": "100%",
                  "isTransparent": false,
                  "height": "100%",
                  "symbol": "$cleanSymbol",
                  "showIntervalTabs": true,
                  "displayMode": "multiple",
                  "locale": "en",
                  "colorTheme": "$theme"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildFinancialsHtml(symbol: String, isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        val cleanSymbol = symbol.ifBlank { "TSX:SHOP" }
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-financials.js" async>
                {
                  "symbol": "$cleanSymbol",
                  "colorTheme": "$theme",
                  "displayMode": "regular",
                  "isTransparent": false,
                  "locale": "en",
                  "width": "100%",
                  "height": "100%"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun buildCompanyProfileHtml(symbol: String, isDark: Boolean): String {
        val theme = getTheme(isDark)
        val bg = getBgColor(isDark)
        val cleanSymbol = symbol.ifBlank { "TSX:SHOP" }
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
              <style>
                html, body {
                  margin: 0;
                  padding: 0;
                  width: 100%;
                  height: 100%;
                  overflow: auto;
                  background-color: $bg;
                }
                .tradingview-widget-container {
                  width: 100% !important;
                  height: 100% !important;
                }
                .tradingview-widget-container__widget {
                  width: 100% !important;
                  height: 100% !important;
                }
              </style>
            </head>
            <body>
              <div class="tradingview-widget-container">
                <div class="tradingview-widget-container__widget"></div>
                <script type="text/javascript" src="https://s3.tradingview.com/external-embedding/embed-widget-symbol-profile.js" async>
                {
                  "width": "100%",
                  "height": "100%",
                  "colorTheme": "$theme",
                  "isTransparent": false,
                  "symbol": "$cleanSymbol",
                  "locale": "en"
                }
                </script>
              </div>
            </body>
            </html>
        """.trimIndent()
    }
}
