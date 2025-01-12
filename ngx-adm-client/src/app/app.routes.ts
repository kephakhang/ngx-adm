import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: "auth",
    loadChildren: () => import("./auth/auth.component").then((m) => m.AuthComponent),
  },
  {
    path: "pages",
    loadChildren: () =>
      import("./pages/pages.routes").then((m) => m.routes),
  },
  { path: "", redirectTo: "pages", pathMatch: "full" },
  { path: "**", redirectTo: "pages" }
];
