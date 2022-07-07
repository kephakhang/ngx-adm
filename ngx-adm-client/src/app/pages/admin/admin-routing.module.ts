import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { AdminComponent } from "./admin.component";
import { UserTableComponent } from "./user/user.component";
import { TenantTableComponent } from "./tenant/tenant.component";
import { TerminalTableComponent } from "./terminal/terminal.component";
import { CounterTableComponent } from "./counter/counter.component";

const routes: Routes = [
  {
    path: "",
    component: AdminComponent,
    children: [
      {
        path: "users",
        component: UserTableComponent,
      },
      {
        path: "tenants",
        component: TenantTableComponent,
      },
      {
        path: "terminals",
        component: TerminalTableComponent,
      },
      {
        path: "counters",
        component: CounterTableComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}

export const routedComponents = [
  AdminComponent,
  UserTableComponent,
  TenantTableComponent,
  TerminalTableComponent,
  CounterTableComponent,
];
