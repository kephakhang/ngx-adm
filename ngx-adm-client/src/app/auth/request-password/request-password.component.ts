import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RequestPasswordData } from '../model/request-password-data'
import { AuthServiceProvider } from '../../providers/auth-service/auth-service';

@Component({
  selector: 'ngx-request-password',
  templateUrl: './request-password.component.html',
  styleUrls: ['./request-password.component.scss']
})
export class RequestPasswordComponent implements OnInit {
  showMessages = {
    error: 'Cannot send the request of updating password !!!',
    success: 'The request of updating password is successful !!'
  };
  strategy: string;
  errors: string[];
  messages: string[];
  user: RequestPasswordData = new RequestPasswordData('');
  confirmPassword: string;
  submitted: boolean = false;
  id: any = {
    dirty: false,
    invalid: false,
    touched: false,
    errors: []
  };
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

  requestPass(): void {

  }
}
