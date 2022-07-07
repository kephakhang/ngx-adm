import { Component, OnInit } from '@angular/core';
import { AuthServiceProvider } from '../../../providers/auth-service/auth-service';

@Component({
  selector: 'ngx-terms',
  templateUrl: './terms.component.html',
  styleUrls: ['./terms.component.scss']
})
export class TermsComponent implements OnInit {

  constructor(public auth: AuthServiceProvider) {
    console.log('What is wrong ?')
   }

  ngOnInit(): void {
  }

  public close(): void {
    this.auth.goBack()
  }

}
