import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { CurrencyService } from '../shared/currency.service';

@Component({
  selector: 'app-currency-calculator',
  standalone: true,
  templateUrl: './currency-calculator.component.html',
  styleUrls: ['./currency-calculator.component.css'],
  imports: [CommonModule, FormsModule]
})
export class CurrencyCalculatorComponent implements OnInit {
  amount: number | null = 0;
  fromCurrency = '';
  toCurrency = '';
  currencies: string[] = [];
  result: { rate: number; converted: number; from: string } | null = null;
  error: string | null = null;

  private http = inject(HttpClient);
  private currencyService = inject(CurrencyService);

  ngOnInit(): void {
    this.currencyService.getCurrencyList().subscribe({
      next: (data) => {
        this.currencies = data;
        this.fromCurrency = data.includes('EUR') ? 'EUR' : data[0];
        this.toCurrency = data.includes('USD') ? 'USD' : data[1];
      },
      error: () => (this.error = 'Failed to load currencies.')
    });
  }

  clearAmountIfZero(): void {
    if (this.amount === 0 || this.amount === null) {
      this.amount = null;
    }
  }

  convert(): void {
    if (!this.amount || !this.fromCurrency || !this.toCurrency) {
      this.error = 'Amount and both currencies are required.';
      return;
    }

    this.http.get<any>(`${environment.apiUrl}/calc`, {
      params: {
        amount: this.amount,
        from: this.fromCurrency,
        to: this.toCurrency
      }
    }).subscribe({
      next: (data) => {
        this.result = data;
        this.error = null;
      },
      error: (err) => {
        this.error = err.error?.error || 'Conversion failed';
        this.result = null;
      }
    });
  }
}
