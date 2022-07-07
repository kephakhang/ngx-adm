/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
import { Common } from "../app/providers/common/common";

export const environment = {
  production: false,
  apiHostUrl: "http://localhost:8080",
  emailIdOnly: true,
  socialLogin: false,
  defaultTheme: "material-dark",
  factory: "yps-own-kr",
  password: {
    min: 7,
    max: 32,
  },
  name: {
    min: 3,
    max: 64,
  },
};
