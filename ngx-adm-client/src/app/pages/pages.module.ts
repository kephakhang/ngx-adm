import {
  NgModule,
  CUSTOM_ELEMENTS_SCHEMA,
  ChangeDetectionStrategy,
} from "@angular/core";
import { NbMenuModule, NbTabsetModule } from "@nebular/theme";

import { ThemeModule } from "../@theme/theme.module";
import { PagesComponent } from "./pages.component";
import { DashboardModule } from "./dashboard/dashboard.module";
import { AdminModule } from "./admin/admin.module";
import { PagesRoutingModule } from "./pages-routing.module";
import { MiscellaneousModule } from "./miscellaneous/miscellaneous.module";

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    AdminModule,
    MiscellaneousModule,
  ],
  declarations: [PagesComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PagesModule {}
