/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { Component, OnInit, LOCALE_ID, Inject } from "@angular/core";
import { AnalyticsService } from "./@core/utils/analytics.service";
import { SeoService } from "./@core/utils/seo.service";
import { AuthServiceProvider } from "./providers/auth-service/auth-service";
import { Common } from "./providers/common/common";

@Component({
  selector: "app-root",
  template: "<router-outlet></router-outlet>",
})
export class AppComponent implements OnInit {
  constructor(
    private auth: AuthServiceProvider,
    private analytics: AnalyticsService,
    private seoService: SeoService,
    @Inject(LOCALE_ID) public locale: string
  ) {}

  async ngOnInit() {
    // this.analytics.trackPageViews();
    // this.seoService.trackCanonicalChanges();
    this.auth.getSession().then(
      (value) => {
        // do Nothings
        this.auth.getTenantList();
      },
      (err) => {
        console.log(this.auth.router.url);
        const str: string[] = window.location.href.split("#");
        const uri = str.length === 2 ? str[1] : "/";
        this.auth.setStorage(Common.URI, uri).then((value) => {
          this.auth.logout();
        });
      }
    );
  }
}
