import { Routes } from '@angular/router';
import { PagesComponent } from './pages.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
export const routes: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
        {
            path: "dashboard",
            loadChildren: () =>
            import("./dashboard/dashboard.component").then((m) => m.DashboardComponent),
        },
        {
            path: "admin",
            loadChildren: () =>
            import("./admin/admin.component").then((m) => m.AdminComponent),
        },
        {
            path: "miscellaneous",
            loadChildren: () =>
              import("./miscellaneous/miscellaneous.component").then(
                (m) => m.MiscellaneousComponent
              ),
        },
        {
            path: "",
            redirectTo: "dashboard",
            pathMatch: "full",
        },
        {
            path: "**",
            component: NotFoundComponent,
        },
    ],
  },
];