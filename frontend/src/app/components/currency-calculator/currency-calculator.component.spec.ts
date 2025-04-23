import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CurrencyCalculatorComponent } from './currency-calculator.component';

describe('CurrencyCalculatorComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CurrencyCalculatorComponent]
    }).compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(CurrencyCalculatorComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
