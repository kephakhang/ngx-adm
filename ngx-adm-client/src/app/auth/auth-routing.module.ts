import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";

import { AuthComponent } from "./auth.component";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { LogoutComponent } from "./logout/logout.component";
import { RequestPasswordComponent } from "./request-password/request-password.component";
import { ResetPasswordComponent } from "./reset-password/reset-password.component";
import { NotFoundComponent } from "../pages/miscellaneous/not-found/not-found.component";
import { TermsComponent } from "./register/terms/terms.component";

const routes: Routes = [
  {
    path: "",
    component: AuthComponent,
    children: [
      {
        path: "login",
        component: LoginComponent,
      },
      {
        path: "register",
        component: RegisterComponent,
      },
      {
        path: "register/terms",
        component: TermsComponent,
      },
      {
        path: "logout",
        component: LogoutComponent,
      },
      {
        path: "request-password",
        component: RequestPasswordComponent,
      },
      {
        path: "reset-password",
        component: ResetPasswordComponent,
      },
      {
        path: "**",
        component: NotFoundComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
