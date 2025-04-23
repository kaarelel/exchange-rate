import { test, expect } from '@playwright/test';

test('Currency conversion flow works', async ({ page }) => {
  await page.goto('/');
  await page.fill('#amount', '15');
  await page.selectOption('#from', 'EUR');
  await page.selectOption('#to', 'USD');
  await page.click('text=Convert');

  await expect(page.locator('text=Converted:')).toBeVisible();
  await expect(page.locator('text=Rate: 1 EUR')).toBeVisible();
});
