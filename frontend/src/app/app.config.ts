import {ApplicationConfig, EnvironmentProviders, Provider} from '@angular/core';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';

export const appConfig: { standaloneComponents: AppComponent[]; providers: (EnvironmentProviders | Provider[])[] } = {
  providers: [
    provideHttpClient(withFetch()),
    provideAnimations()
  ],
  standaloneComponents: [AppComponent]
};
