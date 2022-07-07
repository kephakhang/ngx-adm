import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ResetPasswordData } from '../model/reset-password-data';
import { AuthServiceProvider } from '../../providers/auth-service/auth-service';
import { resetFakeAsyncZone } from '@angular/core/testing';

@Component({
  selector: 'ngx-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  showMessages = {
    error: 'Cannot reset the password !!!',
    success: 'Reset of the password is successful !!'
  };
  strategy: string;
  errors: string[];
  messages: string[];
  user: ResetPasswordData = new ResetPasswordData('');
  submitted: boolean;
  confirmPassword: string;
  password: any = {
    dirty: false,
    invalid: false,
    touched: false,
    errors: []
  };
  rePass: any = {
    dirty: false,
    invalid: false,
    touched: false,
    errors: []
  };

  constructor(public auth: AuthServiceProvider, private cd: ChangeDetectorRef) { }

  ngOnInit(): void {
  }

  resetPass(): void {

  }

}
