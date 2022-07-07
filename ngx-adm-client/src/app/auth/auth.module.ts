import { NgModule, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { ThemeModule } from '../@theme/theme.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { RequestPasswordComponent } from './request-password/request-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { NB_WINDOW, NbAlertModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbIconModule, NbInputModule, NbLayoutModule } from '@nebular/theme';
import { TermsComponent } from './register/terms/terms.component';

@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    RegisterComponent,
    RequestPasswordComponent,
    ResetPasswordComponent,
    TermsComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ThemeModule,
    FormsModule,
    NbAlertModule,
    NbButtonModule,
    NbCardModule,
    NbCheckboxModule,
    NbIconModule,
    NbInputModule,
    NbLayoutModule
  ],
  exports: [
    CommonModule,
    FormsModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
})
export class AuthModule {
}
