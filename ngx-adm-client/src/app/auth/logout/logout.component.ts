import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthServiceProvider } from '../../providers/auth-service/auth-service';

@Component({
  selector: 'ngx-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {
  ngFormGroup: FormGroup;

  constructor(public auth: AuthServiceProvider) { 
    this.auth.logout()
  }

  ngOnInit(): void {

  }

}
