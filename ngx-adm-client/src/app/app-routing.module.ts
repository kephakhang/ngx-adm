import {
  ExtraOptions,
  RouterModule,
  Routes,
  PreloadAllModules,
} from "@angular/router";
import { NgModule } from "@angular/core";
// import { LoginComponent } from './auth/login/login.component'
// import { LogoutComponent } from './auth/logout/logout.component'
// import { RegisterComponent } from './auth/register/register.component'
// import { RequestPasswordComponent } from './auth/request-password/request-password.component'
// import { ResetPasswordComponent } from './auth/reset-password/reset-password.component'
//   NbAuthComponent,
//   NbLoginComponent,
//   NbLogoutComponent,
//   NbRegisterComponent,
//   NbRequestPasswordComponent,
//   NbResetPasswordComponent,
// } from './auth';

export const routes: Routes = [
  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),
  },
  {
    path: "pages",
    loadChildren: () =>
      import("./pages/pages.module").then((m) => m.PagesModule),
  },
  { path: "", redirectTo: "pages", pathMatch: "full" },
  { path: "**", redirectTo: "pages" },
];

const config: ExtraOptions = {
  useHash: true,
  enableTracing: false,
  preloadingStrategy: PreloadAllModules,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
