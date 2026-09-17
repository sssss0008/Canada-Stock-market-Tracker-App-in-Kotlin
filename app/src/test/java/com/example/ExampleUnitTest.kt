package com.example

import com.example.model.CanadianMarketData
import com.example.widget.TradingViewHtmlBuilder
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testHeatmapDataSourceIsALLCA() {
    val html = TradingViewHtmlBuilder.buildHeatmapHtml(isDark = true)
    assertTrue("Heatmap HTML must use dataSource ALLCA for Canadian stocks", html.contains("\"dataSource\": \"ALLCA\""))
    assertTrue("Heatmap HTML must contain stock click interception bridge", html.contains("window.AndroidBridge.onSymbolClicked"))
  }

  @Test
  fun testScreenerContainsClickInterception() {
    val html = TradingViewHtmlBuilder.buildScreenerHtml(isDark = true)
    assertTrue("Screener HTML must contain stock click interception bridge", html.contains("window.AndroidBridge.onSymbolClicked"))
  }

  @Test
  fun testExtractSymbolFromUrl() {
    assertEquals("TSX:SHOP", CanadianMarketData.extractSymbolFromUrl("https://www.tradingview.com/symbols/TSX-SHOP/"))
    assertEquals("TSX:RY", CanadianMarketData.extractSymbolFromUrl("https://www.tradingview.com/symbols/TSX-RY/"))
    assertEquals("TSX:TD", CanadianMarketData.extractSymbolFromUrl("https://www.tradingview.com/symbols/TSX-TD/"))
    assertEquals("TSX:ENB", CanadianMarketData.extractSymbolFromUrl("https://www.tradingview.com/chart/?symbol=TSX%3AENB"))
    assertEquals("TSX:SHOP", CanadianMarketData.extractSymbolFromUrl("SHOP"))
    assertEquals("TSX:TD", CanadianMarketData.extractSymbolFromUrl("TSX:TD"))
  }
}
