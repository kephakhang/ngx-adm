/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID } from '@angular/core';
import { IonicStorageModule, Storage } from '@ionic/storage-angular';
import { CoreModule } from './@core/core.module';
import { ThemeModule } from './@theme/theme.module';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RouteReuseStrategy } from '@angular/router';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { Message } from './providers/message/message';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AuthServiceProvider  } from './providers/auth-service/auth-service';
import { AppleSigninModule } from 'ngx-apple-signin';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { environment } from '../environments/environment';
import {
  SocialLoginModule,
  SocialAuthServiceConfig,
  GoogleLoginProvider,
  FacebookLoginProvider,
  MicrosoftLoginProvider
} from 'angularx-social-login';
import {
  NbChatModule,
  NbDatepickerModule,
  NbDialogModule,
  NbMenuModule,
  NbSidebarModule,
  NbToastrModule,
  NbWindowModule,
  NbInputModule,
  NbFormFieldModule,
  NbTabsetModule,
  NbCardModule,
  NbLayoutModule,
  NbTreeGridModule
} from '@nebular/theme';

@NgModule({
  declarations: [AppComponent],
  imports: [
    SocialLoginModule,
    AppleSigninModule,
    FormsModule,
    NbFormFieldModule,
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    NbInputModule,
    NbTabsetModule,
    NbCardModule,
    NbLayoutModule,
    NbTreeGridModule,
    IonicModule.forRoot(),
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot(),
    NbChatModule.forRoot({
      messageGoogleMapKey: 'AIzaSyA_wNuCzia92MAmdLRzmqitRGvCF7wCZPY',
    }),
    CoreModule.forRoot(),
    ThemeModule.forRoot(),
    IonicStorageModule.forRoot({
      name: '__mydb',
      driverOrder: ['indexeddb','sqlite','websql']
    })
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    { provide: LOCALE_ID, useValue: 'en-US' },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    // {
    //   provide: 'SocialAuthServiceConfig',
    //   useValue: {
    //     autoLogin: false,
    //     providers: [
    //       {
    //         id: GoogleLoginProvider.PROVIDER_ID,
    //         provider: new GoogleLoginProvider(
    //           environment.sns.google.clientId
    //         )
    //       },
    //       {
    //         id: FacebookLoginProvider.PROVIDER_ID,
    //         provider: new FacebookLoginProvider(environment.sns.facebook.clientId)
    //       },
    //       {
    //         id: MicrosoftLoginProvider.PROVIDER_ID,
    //         provider: new FacebookLoginProvider(environment.sns.microsoft.clientId)
    //       }
    //     ],
    //     onError: (err) => {
    //       console.log(JSON.stringify(err));
    //     }
    //   } as SocialAuthServiceConfig,
    // },
    HttpClient,
    Message,
    Storage,
    AuthServiceProvider,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
