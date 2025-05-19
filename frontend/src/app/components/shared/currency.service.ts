import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CurrencyService {
  private http = inject(HttpClient);

  getCurrencyList(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiUrl}/rates`);
  }
}
