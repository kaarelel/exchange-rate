# Test info

- Name: currency chart updates on selection
- Location: C:\ExchangeRate\exchange-rate\frontend\e2e\chart.spec.ts:3:5

# Error details

```
Error: locator.selectOption: Test timeout of 30000ms exceeded.
Call log:
  - waiting for locator('[data-testid="currency-select"]')

    at C:\ExchangeRate\exchange-rate\frontend\e2e\chart.spec.ts:8:24
```

# Page snapshot

```yaml
- heading "Exchange Rate Portal" [level=1]
- text: "Amount:"
- spinbutton "Amount:": "0"
- text: "From Currency:"
- combobox "From Currency:":
  - option "AUD"
  - option "BGN"
  - option "BRL"
  - option "CAD"
  - option "CHF"
  - option "CNY"
  - option "CZK"
  - option "DKK"
  - option "EUR" [selected]
  - option "GBP"
  - option "HKD"
  - option "HUF"
  - option "IDR"
  - option "ILS"
  - option "INR"
  - option "ISK"
  - option "JPY"
  - option "KRW"
  - option "MXN"
  - option "MYR"
  - option "NOK"
  - option "NZD"
  - option "PHP"
  - option "PLN"
  - option "RON"
  - option "SEK"
  - option "SGD"
  - option "THB"
  - option "TRY"
  - option "USD"
  - option "ZAR"
- text: "To Currency:"
- combobox "To Currency:":
  - option "AUD"
  - option "BGN"
  - option "BRL"
  - option "CAD"
  - option "CHF"
  - option "CNY"
  - option "CZK"
  - option "DKK"
  - option "EUR"
  - option "GBP"
  - option "HKD"
  - option "HUF"
  - option "IDR"
  - option "ILS"
  - option "INR"
  - option "ISK"
  - option "JPY"
  - option "KRW"
  - option "MXN"
  - option "MYR"
  - option "NOK"
  - option "NZD"
  - option "PHP"
  - option "PLN"
  - option "RON"
  - option "SEK"
  - option "SGD"
  - option "THB"
  - option "TRY"
  - option "USD" [selected]
  - option "ZAR"
- button "Convert"
- heading "Exchange Rate History" [level=2]
- text: "Currency:"
- combobox "Currency:":
  - option "AUD"
  - option "BGN"
  - option "BRL"
  - option "CAD"
  - option "CHF"
  - option "CNY"
  - option "CZK"
  - option "DKK"
  - option "EUR"
  - option "GBP"
  - option "HKD"
  - option "HUF"
  - option "IDR"
  - option "ILS"
  - option "INR"
  - option "ISK"
  - option "JPY"
  - option "KRW"
  - option "MXN"
  - option "MYR"
  - option "NOK"
  - option "NZD"
  - option "PHP"
  - option "PLN"
  - option "RON"
  - option "SEK"
  - option "SGD"
  - option "THB"
  - option "TRY"
  - option "USD" [selected]
  - option "ZAR"
```

# Test source

```ts
   1 | import { test, expect } from '@playwright/test';
   2 |
   3 | test('currency chart updates on selection', async ({ page }) => {
   4 |   await page.goto('http://localhost:4200');
   5 |
   6 |   const currencySelect = page.locator('[data-testid="currency-select"]');
   7 |   //await expect(currencySelect).toBeVisible();
>  8 |   await currencySelect.selectOption('USD');
     |                        ^ Error: locator.selectOption: Test timeout of 30000ms exceeded.
   9 |
  10 |   await page.waitForTimeout(1000);
  11 |   await expect(page.locator('canvas')).toBeVisible();
  12 | });
  13 |
```