package com.example.model

/**
 * Financial Dictionary Term for TSX / Canadian & Global Equity Markets.
 */
data class FinancialDictionaryTerm(
    val term: String,
    val category: String,
    val shortDefinition: String,
    val detailedExplanation: String,
    val canadianContext: String? = null,
    val formulaOrExample: String? = null
)

object CanadianFinancialDictionary {

    val categories = listOf(
        "All",
        "Canadian Accounts",
        "Valuation & Metrics",
        "Technical Analysis",
        "Dividends & Income",
        "Risk & Macro"
    )

    val terms = listOf(
        FinancialDictionaryTerm(
            term = "TFSA (Tax-Free Savings Account)",
            category = "Canadian Accounts",
            shortDefinition = "A registered Canadian savings account where capital gains, dividends, and interest are 100% tax-free.",
            detailedExplanation = "Introduced by the federal government of Canada in 2009. Canadians aged 18+ accumulate annual contribution room. Withdrawals can be made at any time tax-free, and the withdrawn amount is added back to your contribution room on January 1st of the following calendar year.",
            canadianContext = "Eligible for TSX, TSXV stocks, Canadian REITs, and ETFs. Over-contributions face a 1% per month penalty from CRA.",
            formulaOrExample = "2024 Contribution Limit: \$7,000 CAD. Total cumulative room since 2009: \$95,000 CAD."
        ),
        FinancialDictionaryTerm(
            term = "RRSP (Registered Retirement Savings Plan)",
            category = "Canadian Accounts",
            shortDefinition = "A tax-advantaged account designed for retirement savings where contributions reduce your taxable income.",
            detailedExplanation = "Contributions made into an RRSP directly deduct from your Canadian taxable employment income for that year. Growth compounds tax-deferred until withdrawal in retirement, at which point it is taxed at your marginal tax bracket.",
            canadianContext = "Holding US dividend payers (e.g., Apple, Microsoft) in an RRSP exempts them from the 15% US IRS foreign withholding tax under the Canada-US tax treaty.",
            formulaOrExample = "Annual deduction limit: 18% of earned income from the previous year, up to statutory maximum (\$31,560 for 2024)."
        ),
        FinancialDictionaryTerm(
            term = "FHSA (First Home Savings Account)",
            category = "Canadian Accounts",
            shortDefinition = "A registered account combining the best features of RRSP tax deduction and TFSA tax-free withdrawals for home buyers.",
            detailedExplanation = "Launched by the Canadian government in 2023. Contributions are tax-deductible (like an RRSP), and qualified withdrawals for buying a first qualifying home in Canada are completely tax-free (like a TFSA).",
            canadianContext = "Lifetime maximum contribution limit of \$40,000 CAD with an annual cap of \$8,000 CAD per year.",
            formulaOrExample = "Max \$8,000/year up to \$40,000 lifetime."
        ),
        FinancialDictionaryTerm(
            term = "Dividend Yield",
            category = "Dividends & Income",
            shortDefinition = "The annual cash dividend payout expressed as a percentage of the stock's current share price.",
            detailedExplanation = "A fundamental metric heavily favored by Canadian investors, especially in the Big 6 Canadian Banks (RY, TD, BNS, BMO, CM, NA) and Canadian pipeline/energy giants (ENB, TRP).",
            canadianContext = "Canadian eligible dividends benefit from the Federal and Provincial Dividend Tax Credit (DTC) in non-registered taxable accounts.",
            formulaOrExample = "Dividend Yield (%) = (Annual Dividend Per Share / Current Stock Price) × 100"
        ),
        FinancialDictionaryTerm(
            term = "Dividend Tax Credit (DTC)",
            category = "Dividends & Income",
            shortDefinition = "A Canadian tax mechanism to prevent double taxation on dividends paid by Canadian public corporations.",
            detailedExplanation = "When a Canadian corporation pays income tax, its distributions to Canadian resident shareholders receive a gross-up (e.g. 38% for eligible dividends) and an offsetting non-refundable federal + provincial tax credit, lowering effective tax rates significantly compared to interest income.",
            canadianContext = "Makes Canadian blue chips (Enbridge, Bell, Royal Bank) remarkably tax-efficient in non-registered accounts.",
            formulaOrExample = "Eligible Dividend Gross-up: 138% of actual dividend; Federal Credit: 15.0198% of grossed-up dividend."
        ),
        FinancialDictionaryTerm(
            term = "P/E Ratio (Price-to-Earnings)",
            category = "Valuation & Metrics",
            shortDefinition = "Ratio measuring a company's current share price relative to its per-share net earnings.",
            detailedExplanation = "Indicates the dollar amount an investor expects to invest in a company in order to receive one dollar of that company’s earnings. High P/E reflects strong expected future growth, while low P/E can signal undervaluation or cyclical peak earnings.",
            canadianContext = "TSX 60 typically trades at a lower historical P/E (14-16x) than the S&P 500 (20-24x) due to the heavy weighting of financials, energy, and materials.",
            formulaOrExample = "P/E = Current Stock Price / Diluted Earnings Per Share (EPS)"
        ),
        FinancialDictionaryTerm(
            term = "P/B Ratio (Price-to-Book)",
            category = "Valuation & Metrics",
            shortDefinition = "Compares a firm's market capitalization to its book value (net assets minus liabilities).",
            detailedExplanation = "Crucial for evaluating asset-heavy sectors such as Canadian commercial banks (RBC, TD, BMO) and mining/energy infrastructure. A P/B under 1.0 indicates a stock is trading below the accounting net value of its physical assets.",
            canadianContext = "Standard benchmark for Canadian financial institutions and natural resource explorers.",
            formulaOrExample = "P/B = Market Price Per Share / Book Value Per Share"
        ),
        FinancialDictionaryTerm(
            term = "EBITDA & Enterprise Value (EV)",
            category = "Valuation & Metrics",
            shortDefinition = "Earnings Before Interest, Taxes, Depreciation, and Amortization; paired with EV to measure total acquisition cost.",
            detailedExplanation = "Enterprise Value factors in both equity market cap and net debt (total debt minus cash). EV/EBITDA provides a capital-structure-neutral valuation metric heavily used in oil & gas mergers and Canadian midstream acquisitions.",
            canadianContext = "Commonly used across the Calgary oil patch and TSX Materials sector.",
            formulaOrExample = "EV = Market Cap + Total Debt - Cash & Cash Equivalents"
        ),
        FinancialDictionaryTerm(
            term = "RSI (Relative Strength Index)",
            category = "Technical Analysis",
            shortDefinition = "A momentum oscillator measuring the speed and change of recent price movements on a 0-100 scale.",
            detailedExplanation = "Developed by J. Welles Wilder. Readings above 70 typically signal overbought conditions (potential pullback or consolidation), while readings below 30 signal oversold conditions (potential bounce).",
            canadianContext = "Frequently paired with TSX volume spikes around Bank of Canada rate announcement days.",
            formulaOrExample = "RSI = 100 - [100 / (1 + (Average Gain / Average Loss))]"
        ),
        FinancialDictionaryTerm(
            term = "MACD (Moving Average Convergence Divergence)",
            category = "Technical Analysis",
            shortDefinition = "A trend-following momentum indicator showing the relationship between two exponential moving averages.",
            detailedExplanation = "Calculated by subtracting the 26-period EMA from the 12-period EMA. A 9-period EMA of the MACD is plotted as a 'signal line'. Bullish crossovers occur when MACD crosses above the signal line.",
            canadianContext = "Popular among Canadian swing traders on volatile TSX Venture mining equities.",
            formulaOrExample = "MACD Line = 12-day EMA - 26-day EMA; Signal Line = 9-day EMA of MACD Line"
        ),
        FinancialDictionaryTerm(
            term = "Capital Gain & Inclusion Rate",
            category = "Risk & Macro",
            shortDefinition = "The profit realized from selling a capital property (like TSX shares) above its adjusted cost base (ACB).",
            detailedExplanation = "In Canada, only a portion of capital gains is included in taxable income. Starting June 25, 2024, individuals are taxed on 50% of capital gains up to \$250,000 annually, and 66.67% on capital gains exceeding \$250,000 in a year.",
            canadianContext = "Important for Canadian taxable brokerage accounts. Capital losses can be carried back 3 years or forward indefinitely to offset capital gains.",
            formulaOrExample = "Capital Gain = Net Proceeds - Adjusted Cost Base (ACB) - Selling Commissions"
        ),
        FinancialDictionaryTerm(
            term = "BOC Overnight Rate",
            category = "Risk & Macro",
            shortDefinition = "The key interest rate target set by the Bank of Canada for commercial banks lending funds overnight.",
            detailedExplanation = "The primary monetary policy tool used by the Bank of Canada to steer inflation toward its 2% target. Influences Canadian Prime rates, variable mortgage rates, GIC returns, and corporate borrowing costs.",
            canadianContext = "Directly impacts TSX Banking margins, REIT debt refinancing burdens, and the CAD/USD exchange rate.",
            formulaOrExample = "BOC Policy Interest Rate determines Canadian Prime Rate (typically Prime = BOC Rate + 2.20%)."
        ),
        FinancialDictionaryTerm(
            term = "Adjusted Cost Base (ACB)",
            category = "Canadian Accounts",
            shortDefinition = "The tax-adjusted cost of an investment asset used to calculate capital gains or losses upon disposition.",
            detailedExplanation = "In Canada, each purchase, reinvested dividend (DRIP), and return of capital (ROC) from REITs or ETFs modifies the average cost per share across all non-registered accounts.",
            canadianContext = "Crucial for Canadian ETF investors to accurately track phantom distributions and ROC distributions.",
            formulaOrExample = "ACB Per Share = Total Capital Invested (including commissions) / Total Shares Held"
        ),
        FinancialDictionaryTerm(
            term = "DRIP (Dividend Reinvestment Plan)",
            category = "Dividends & Income",
            shortDefinition = "A program allowing shareholders to automatically reinvest cash dividends into additional shares.",
            detailedExplanation = "Enables friction-free compound growth with no brokerage trading commissions. Many Canadian corporations (e.g., Telus, Emera, Canadian Utilities) offer a treasury DRIP discount of 2% to 5% on purchase price.",
            canadianContext = "Eligible in TFSA, RRSP, and non-registered accounts. In taxable accounts, reinvested dividends still count as taxable income and increase ACB.",
            formulaOrExample = "New Shares Acquired = Net Cash Dividend Distributed / Discounted Market Share Price"
        )
    )
}
