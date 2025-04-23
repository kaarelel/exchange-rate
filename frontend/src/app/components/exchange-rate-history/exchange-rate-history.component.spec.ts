import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ExchangeRateHistoryComponent } from './exchange-rate-history.component';

describe('ExchangeRateHistoryComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ExchangeRateHistoryComponent]
    }).compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(ExchangeRateHistoryComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
