import { Component } from '@angular/core';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">
      Copyrightⓒ ♥ by <b><a href="https://www.younplussoft.com" target="_blank">YoungPlusSoft</a></b> 2022
    </span>
    <div class="socials">
      <a href="https://github.com/YoungPlusSoft" target="_blank" class="ion ion-social-github"></a>
      <a href="https://www.facebook.com/YoungPlusSoftglobal" target="_blank" class="ion ion-social-facebook"></a>
      <a href="https://twitter.com/YoungPlusSoft" target="_blank" class="ion ion-social-twitter"></a>
      <a href="https://www.linkedin.com/company/YoungPlusSoft/mycompany" target="_blank" class="ion ion-social-linkedin"></a>
    </div>
  `,
})
export class FooterComponent {
}
