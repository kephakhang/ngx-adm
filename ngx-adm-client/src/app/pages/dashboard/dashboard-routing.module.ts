import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { DashboardComponent } from "./dashboard.component";
import { IotComponent } from "./iot/iot.component";

const routes: Routes = [
  {
    path: "",
    component: IotComponent,
    children: [
      {
        path: "iot",
        component: IotComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}

export const routedComponents = [IotComponent, DashboardComponent];
