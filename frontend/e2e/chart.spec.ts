import { test, expect } from '@playwright/test';

test('currency chart updates on selection', async ({ page }) => {
  await page.goto('http://localhost:4200');

  const currencySelect = page.locator('[data-testid="currency-select"]');
  await expect(currencySelect).toBeVisible();
  await currencySelect.selectOption('USD');

  await page.waitForTimeout(1000);
  await expect(page.locator('canvas')).toBeVisible();
});
