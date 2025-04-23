import { ChartConfiguration } from 'chart.js';

export const defaultChartData: {
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

export const defaultChartOptions: ChartConfiguration<'line'>['options'] = {
  responsive: true,
  maintainAspectRatio: false
};
