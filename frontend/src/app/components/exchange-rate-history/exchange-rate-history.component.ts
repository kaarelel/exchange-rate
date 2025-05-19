import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective, provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { environment } from '../../../environments/environment';
import { CurrencyService } from '../shared/currency.service';

@Component({
  selector: 'app-exchange-rate-history',
  standalone: true,
  templateUrl: './exchange-rate-history.component.html',
  styleUrls: ['./exchange-rate-history.component.css'],
  imports: [CommonModule, FormsModule, BaseChartDirective],
  providers: [provideCharts(withDefaultRegisterables())]
})
export class ExchangeRateHistoryComponent implements OnInit {
  currency = '';
  currencyList: string[] = [];

  lineChartData: {
    datasets: { tension: number; data: any[]; borderWidth: number; label: string; fill: boolean }[];
    labels: any[]
  } = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Exchange Rate',
        fill: true,
        tension: 0.4,
        borderWidth: 2
      }
    ]
  };
  lineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    maintainAspectRatio: false
  };
  lineChartType: 'line' = 'line';

  private http = inject(HttpClient);
  private currencyService = inject(CurrencyService);

  ngOnInit(): void {
    this.currencyService.getCurrencyList().subscribe({
      next: (data) => {
        this.currencyList = data;
        this.currency = data.includes('USD') ? 'USD' : data[0];
        this.loadChartData();
      },
      error: () => console.error('Failed to load currency list')
    });
  }

  onCurrencyChange(): void {
    this.loadChartData();
  }

  loadChartData(): void {
    this.http.get<{ date: string; rate: number }[]>(`${environment.apiUrl}/history/${this.currency}`).subscribe({
      next: (data) => {
        this.lineChartData = {
          labels: data.map(d => d.date),
          datasets: [{
            data: data.map(d => d.rate),
            label: 'Exchange Rate',
            fill: true,
            tension: 0.4,
            borderWidth: 2
          }]
        };
      },
      error: () => console.error('Failed to load chart data')
    });
  }
}

