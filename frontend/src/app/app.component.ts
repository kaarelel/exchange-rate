import { Component } from '@angular/core';
import { CurrencyCalculatorComponent } from './components/currency-calculator/currency-calculator.component';
import { ExchangeRateHistoryComponent } from './components/exchange-rate-history/exchange-rate-history.component';

@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <h1>Exchange Rate Portal</h1>
    <app-currency-calculator />
    <app-exchange-rate-history />
  `,
  imports: [CurrencyCalculatorComponent, ExchangeRateHistoryComponent]
})
export class AppComponent {}
