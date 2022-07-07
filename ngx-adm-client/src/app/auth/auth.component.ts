import { Component } from '@angular/core';

@Component({
  selector: 'nb-auth',
  styleUrls: ['auth.component.scss'],
  template: `
    <ngx-no-sidebar-layout>
      <router-outlet></router-outlet>
    </ngx-no-sidebar-layout>
  `,
})
export class AuthComponent {

}
