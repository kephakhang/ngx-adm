
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
