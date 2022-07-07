import { environment } from "./../../../environments/environment";
import { Common } from "./../common/common";
import { Location } from "@angular/common";
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Message } from "../message/message";
import { Router, ActivatedRoute, NavigationExtras } from "@angular/router";
import {
  NbDialogService,
  NbToastrService,
  NbThemeService,
  NbDateTimeAdapterService,
} from "@nebular/theme";
import {
  AlertController,
  Platform,
  LoadingController,
  ModalController,
} from "@ionic/angular";
import { Storage } from "@ionic/storage-angular";
import { TenantData } from "../../model/tenant-data";
import { CounterData } from "../../model/counter-data";
import { UserLevel } from "app/enum/user-level";

/*
  Generated class for the this provider.
  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable({
  providedIn: "root",
})
export class AuthServiceProvider {
  public apiHostUrl = environment.apiHostUrl;
  public lang = "en";
  public confirmStr = "Confirm";
  public notificationStr = "Notification";
  public okStr = "Ok";
  public errorStr = "Error";
  public cancelStr = "Cancel";
  public closeStr = "Close";
  public package: string = null;
  public version: string = null;
  public root: string = "/";
  public baseHref: string = "/";
  public title: string = "";
  public user: any = null;
  private storage: Storage | null = null;
  fileChooser: any;
  private sessionNotFound: string = "YoungPlusSoft ::: Session Not Found !!!";
  private userNotFound: string = "YoungPlusSoft ::: User Not Found !!!";

  async init() {
    // If using, define drivers here: await this.storage.defineDriver(/*...*/);
    //this._storage.defineDriver()
    const storage = await this._storage.create();
    this.storage = storage;
  }

  constructor(
    public router: Router,
    public modal: ModalController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    public ahttp: HttpClient,
    public platform: Platform,
    public message: Message,
    public location: Location,
    public theme: NbThemeService,
    public _storage: Storage,
    public activateRoute: ActivatedRoute
  ) {
    this.init();

    this.platform.ready().then((data) => {

      this.root = "";
      this.baseHref = "./";
      this.package = "com.youngplussoft.client";
      this.version = "1.0.0";

      let lang = this.getUsersLocale("en_US").split("_")[0];
      this.getStorage(Common.LANG).then(
        (value) => {
          if (value) {
            lang = value;
          }

          if (!this.message.langMapArr[lang]) {
            this.lang = "en";
          } else {
            this.lang = lang;
            this.setStorage(Common.LANG, this.lang);
          }
        },
        (err) => {
          this.lang = "en";
        }
      );

      let themeVal: string = environment.defaultTheme;
      this.changeTheme(themeVal);

      this.okStr = this.message.get("global.ok");
      this.errorStr = this.message.get("global.error");
      this.confirmStr = this.message.get("global.confirm");
      this.cancelStr = this.message.get("global.cancel");
      this.closeStr = this.message.get("global.close");
      this.notificationStr = this.message.get("global.notification");
    });
  }

  public getCounterList(
    tenantId?: string,
    terminalId?: string
  ): Promise<CounterData[]> {
    let resolveFunc;
    let rejectFunc;

    const promise = new Promise<CounterData[]>((resolve, reject) => {
      resolveFunc = (list: CounterData[]) => {
        resolve(list);
      };

      rejectFunc = (err: any) => {
        reject(err);
      };
    });

    let params = new HttpParams();

    if (tenantId) {
      params = params.set("tenantId", tenantId);
    }

    if (terminalId) {
      params = params.set("terminalId", terminalId);
    }

    this.get("/api/v1/counter/list", params).then(
      (list: any[]) => {
        const counterList = list.map(
          (item) =>
            new CounterData(
              item.id,
              item.tenantId,
              item.terminalId,
              item.version,
              item.status,
              new Date(item.regDatetime).toLocaleString(),
              new Date(item.modDatetime).toLocaleString()
            )
        );
        resolveFunc(counterList);
      },
      (err) => {
        rejectFunc(err);
      }
    );

    return promise;
  }

  public getTenantList(): Promise<TenantData[]> {
    let resolveFunc;
    let rejectFunc;

    const promise = new Promise<TenantData[]>((resolve, reject) => {
      resolveFunc = (list: TenantData[]) => {
        resolve(list);
      };

      rejectFunc = (err: any) => {
        reject(err);
      };
    });

    this.getStorage(Common.TENANTS).then(
      (list) => {
        if (list) {
          resolveFunc(list);
        } else {
          const params = new HttpParams().set("salt", this.getTimeNow());
          this.get("/api/v1/tenant/list", params).then(
            async (list: any[]) => {
              const tenantList = list.map(
                (item) =>
                  new TenantData(
                    item.id,
                    item.name,
                    item.type,
                    item.description,
                    item.countryCode,
                    item.hostUrl,
                    item.prefix,
                    item.hostname,
                    new Date(item.regDatetime).toLocaleString(),
                    new Date(item.modDatetime).toLocaleString()
                  )
              );
              await this.setStorage(Common.TENANTS, tenantList);
              resolveFunc(list);
            },
            (err) => {
              rejectFunc(err);
            }
          );
        }
      },
      (err) => {
        const params = new HttpParams().set("salt", this.getTimeNow());
        this.get("/api/v1/tenant/list", params).then(
          async (list: any[]) => {
            const tenantList = list.map(
              (item) =>
                new TenantData(
                  item.id,
                  item.name,
                  item.type,
                  item.description,
                  item.countryCode,
                  item.hostUrl,
                  item.prefix,
                  item.hostname,
                  new Date(item.regDatetime).toLocaleString(),
                  new Date(item.modDatetime).toLocaleString()
                )
            );
            await this.setStorage(Common.TENANTS, tenantList);
            resolveFunc(list);
          },
          (err) => {
            rejectFunc(err);
          }
        );
      }
    );

    return promise;
  }

  public setLang(newLang): Promise<any> {
    this.lang = newLang;
    return this.setStorage(Common.LANG, newLang);
  }

  public isValidName(name: string): boolean {
    if (
      name.trim() !== "" &&
      /^[\w'\-,.][^0-9_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~<>;:[\]]{3,}$/.test(name)
    ) {
      return true;
    } else {
      return false;
    }
  }

  public isValidMobile(mobile: string): boolean {
    if (
      mobile.trim() !== "" &&
      /^[+]?[({0,1}[0-9]{1,4}[)]{0,1}[\s]?[-/0-9]*$/.test(mobile)
    ) {
      return true;
    } else {
      return false;
    }
  }

  public isValidEmail(email: string): boolean {
    if (email.trim() !== "" && /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
      return true;
    } else {
      return false;
    }
  }

  public isValidPassword(password: string): boolean {
    if (
      password.trim() !== "" &&
      /^(?=.*[A-Z].)(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{7,32}$/.test(password)
    ) {
      return true;
    } else {
      return false;
    }
  }

  changeTheme(themeName: string) {
    this.theme.changeTheme(themeName);
    localStorage.setItem(Common.THEME, themeName);
  }

  getUsersLocale(defaultValue: string): string {
    if (
      typeof window === "undefined" ||
      typeof window.navigator === "undefined"
    ) {
      return defaultValue;
    }
    const wn = window.navigator as any;
    let lang = wn.languages ? wn.languages[0] : defaultValue;
    lang = lang || wn.language || wn.browserLanguage || wn.userLanguage;
    return lang;
  }

  public setTitle(title) {
    this.title = title;
  }

  public goBack() {
    this.location.back();
  }

  public nvr(data) {
    if (data === undefined || data == null) {
      return "";
    } else {
      return data;
    }
  }

  public goHome() {
    this.router.navigateByUrl("/pages/dashboard");
  }

  public async showError(err: any) {
    try {
      if (err.error && err.error.body) {
        if (err.error.body.message && err.error.body.message[this.lang]) {
          this.presentAlert(err.error.body.message[this.lang]);
        } else if (err.error.body.description) {
          this.presentAlert(err.error.body.description, true);
        } else {
          this.presentAlert(JSON.stringify(err.error.body), true);
        }
      } else if (err.status && err.message) {
        this.presentAlert(err.message, true);
        if (err.status === 401) {
          this.logout();
        }
      } else if (err.message) {
        if (err.status !== undefined && err.status === 0) {
          this.presentAlert(
            "ERR_CONNECTION_REFUSED : cannot connect to the server"
          );
          this.logout();
        } else {
          this.presentAlert(err.message);
        }
      } else if (typeof err === "string") {
        this.presentAlert(err as string);
        if ((err as string) === this.sessionNotFound) {
          this.logout();
        }
      } else {
        this.presentAlert(JSON.stringify(err), true);
      }
    } catch (e) {
      this.presentAlert(JSON.stringify(e), true);
    }
  }

  public async showLoading() {
    const loading = await this.loadingCtrl.create({
      message: "",
      spinner: "bubbles",
      cssClass: "background-color: rgba( 255, 255, 255, 1.0 )",
      translucent: true,
      duration: 2000000000,
    });
    loading.present();
    return loading;
  }

  public async presentAlertWithNick(nick: string) {
    let message = `${nick} : undefined message: Please contact the administrator.`;

    if (nick && nick.trim() !== "") {
      const tmp = this.message.get(nick);
      if (tmp) {
        message = tmp;
      }
    }

    const alert = await this.alertCtrl.create({
      header: this.notificationStr,
      subHeader: "",
      message: message,
      buttons: [this.okStr], // this.auth.message.get('general', 'close')]
    });

    return await alert.present();
  }

  public async presentAlert(message: string, error?: boolean) {
    // const alertController = document.querySelector('ion-alert-controller')
    // await alertController.componentOnReady()

    if (error) {
      const alert = await this.alertCtrl.create({
        header: this.errorStr,
        subHeader: "",
        message: message,
        buttons: [this.okStr], // this.auth.message.get('general', 'close')]
      });
      return await alert.present();
    } else {
      const alert = await this.alertCtrl.create({
        header: this.notificationStr,
        subHeader: "",
        message: message,
        buttons: [this.okStr], // this.auth.message.get('general', 'close')]
      });
      return await alert.present();
    }
  }

  public async promptAlert(nick): Promise<string> {
    let prompt =
      "empty or undefined message: Please contact the administrator.";

    if (!nick && nick.trim() !== "") {
      prompt = this.message.get(nick);
      if (!prompt) {
        prompt = nick;
      }
    }

    let resolveFunction: (confirm: string) => void;
    let rejectFunction: (confirm: string) => void;
    let promise = new Promise<string>((resolve, reject) => {
      resolveFunction = resolve;
      rejectFunction = reject;
    });
    const alert = await this.alertCtrl.create({
      header: this.notificationStr,
      subHeader: "",
      inputs: [
        {
          type: "text",
          name: "description",
          placeholder: "비디오 설명",
          id: "videoDescription",
        },
      ],
      message: prompt,
      cssClass: "alertCustomCss",
      buttons: [
        {
          text: this.cancelStr,
          role: "cancel",
          handler: (data) => {
            rejectFunction(null);
          },
        },
        {
          text: this.okStr,
          role: "ok",
          handler: (data) => {
            if (
              data.description === undefined ||
              data.description == null ||
              data.description.trim() === ""
            ) {
              this.presentAlertWithNick(
                this.message.get("filetransfer.desc.needed")
              );
              return false;
            } else {
              resolveFunction(data.description);
              return true;
            }
          },
        },
      ],
    });
    alert.present();

    return promise;
  }

  public setStorage(key: string, value: any): Promise<any> {
    try {
      return this.storage.set(key, value);
    } catch (ex) {
      return new Promise((resolve, reject) => {
        reject(
          "setStorage() : TypeError: Cannot read properties of null (reading 'set')"
        );
      });
    }
  }

  public getStorage(key: string): Promise<any> {
    try {
      return this.storage.get(key);
    } catch (ex) {
      return new Promise((resolve, reject) => {
        reject(
          "getStorage() : TypeError: Cannot read properties of null (reading 'get')"
        );
      });
    }
  }

  public removeStorage(key: string): Promise<any> {
    try {
      return this.storage.remove(key);
    } catch (ex) {
      return new Promise((resolve, reject) => {
        reject(
          "removeStorage() : TypeError: Cannot read properties of null (reading 'remove')"
        );
      });
    }
  }

  public getTimeNow(): number {
    return new Date().getTime();
  }

  //common/http ================================================== [

  public anyToFormData(
    model: any,
    form: FormData = null,
    namespace = ""
  ): FormData {
    let formData = form || new FormData();
    for (var propertyName in model) {
      if (
        !model.hasOwnProperty(propertyName) ||
        model[propertyName] == undefined
      )
        continue;
      let formKey = namespace ? `${namespace}[${propertyName}]` : propertyName;
      if (model[propertyName] instanceof Date) {
        formData.append(formKey, model[propertyName].toString());
      } else if (model[propertyName] instanceof Array) {
        model[propertyName].forEach((element, index) => {
          if (typeof element != "object")
            formData.append(`${formKey}[]`, element);
          else {
            const tempFormKey = `${formKey}[${index}]`;
            this.anyToFormData(element, formData, tempFormKey);
          }
        });
      } else if (
        typeof model[propertyName] === "object" &&
        !(model[propertyName] instanceof File)
      ) {
        this.anyToFormData(model[propertyName], formData, formKey);
      } else {
        formData.append(formKey, model[propertyName].toString());
      }
    }
    return formData;
  }

  public async get(uri: string, params?: HttpParams, isLoading?: boolean) {
    return new Promise(async (resolve, reject) => {
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: "Bearer " + this.user?.jwt,
      });

      if (isLoading) {
        const loading = await this.showLoading();
        let options: any = { headers: headers };
        if (params !== undefined && params != null) {
          options = { params: params, headers: headers };
        }
        this.ahttp.get(this.apiHostUrl + uri, options).subscribe(
          (res: any) => {
            loading.dismiss();
            resolve(res);
          },
          (err: any) => {
            loading.dismiss();
            reject(err);
          }
        );
      } else {
        let options: any = { headers: headers };
        if (params !== undefined && params != null) {
          options = { params: params, headers: headers };
        }
        this.ahttp.get(this.apiHostUrl + uri, options).subscribe(
          (res: any) => {
            resolve(res);
          },
          (err: any) => {
            reject(err);
          }
        );
      }
    });
  }

  public async delete(uri: string, params?: HttpParams) {
    return new Promise(async (resolve, reject) => {
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: "Bearer " + this.user?.jwt,
        //'Cache-Control': 'no-cache, no-store, must-revalidate'
      });

      let loading = await this.showLoading();
      let options: any = { headers: headers };
      if (params !== undefined && params != null) {
        options = { params: params, headers: headers };
      }

      this.ahttp.delete(this.apiHostUrl + uri, options).subscribe(
        (res: any) => {
          loading.dismiss();
          resolve(res);
        },
        (err: any) => {
          loading.dismiss();
          reject(err);
        }
      );
    });
  }

  public async post2(uri: string, body: any) {
    return new Promise(async (resolve, reject) => {
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "multipart/form-data",
        Authorization: "Bearer " + this.user?.jwt,
        //'Cache-Control': 'no-cache, no-store, must-revalidate'
      });

      const formData: FormData = this.anyToFormData(body);
      const loading = await this.showLoading();
      this.ahttp
        .post(this.apiHostUrl + uri, formData, { headers: headers })
        .subscribe(
          (res: any) => {
            loading.dismiss();
            resolve(res);
          },
          (err) => {
            loading.dismiss();
            reject(err);
          }
        );
    });
  }

  public async post(uri: string, body: any) {
    if (uri.startsWith("/sso/")) {
      return new Promise(async (resolve, reject) => {
        const headers = new HttpHeaders({
          Accept: "application/json;charset=UTF-8",
          "Content-Type": "application/json;charset=UTF-8",
          //'Cache-Control': 'no-cache, no-store, must-revalidate'
        });
        const loading = await this.showLoading();
        this.ahttp
          .post(this.apiHostUrl + uri, body, { headers: headers })
          .subscribe(
            (res: any) => {
              loading.dismiss();
              resolve(res);
            },
            (err) => {
              loading.dismiss();
              reject(err);
            }
          );
      });
    } else {
      return new Promise(async (resolve, reject) => {
        const headers = new HttpHeaders({
          Accept: "application/json;charset=UTF-8",
          "Content-Type": "application/json;charset=UTF-8",
          Authorization: "Bearer " + this.user?.jwt,
          //'Cache-Control': 'no-cache, no-store, must-revalidate'
        });
        const loading = await this.showLoading();
        this.ahttp
          .post(this.apiHostUrl + uri, body, { headers: headers })
          .subscribe(
            (res: any) => {
              loading.dismiss();
              resolve(res);
            },
            (err) => {
              loading.dismiss();
              reject(err);
            }
          );
      });
    }
  }

  public async put2(uri: string, body: any) {
    return new Promise(async (resolve, reject) => {
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "application/json;charset=UTF-8",
        Authorization: "Bearer " + this.user?.jwt,
        //'Cache-Control': 'no-cache, no-store, must-revalidate'
      });

      const formData: FormData = this.anyToFormData(body);
      const loading = await this.showLoading();
      this.ahttp
        .put(this.apiHostUrl + uri, formData, { headers: headers })
        .subscribe(
          (res: any) => {
            loading.dismiss();
            resolve(res);
          },
          (err) => {
            loading.dismiss();
            reject(err);
          }
        );
    });
  }

  public async put(uri: string, body: any) {
    return new Promise(async (resolve, reject) => {
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "application/json;charset=UTF-8",
        Authorization: "Bearer " + this.user?.jwt,
        //'Cache-Control': 'no-cache, no-store, must-revalidate'
      });

      //const formData: FormData = this.anyToFormData(body)
      const loading = await this.showLoading();
      this.ahttp
        .put(this.apiHostUrl + uri, body, { headers: headers })
        .subscribe(
          (res: any) => {
            loading.dismiss();
            resolve(res);
          },
          (err) => {
            loading.dismiss();
            reject(err);
          }
        );
    });
  }

  public async postMultipart(uri: string, body: any, params: URLSearchParams) {
    return new Promise(async (resolve, reject) => {
      if (params === null) {
        params = new URLSearchParams();
      }
      const headers = new HttpHeaders({
        Accept: "application/json;charset=UTF-8",
        "Content-Type": "multipart/form-data",
        Authorization: "Bearer " + this.user?.jwt,
        //'Cache-Control': 'no-cache, no-store, must-revalidate'
      });
      const loading = await this.showLoading();
      this.ahttp
        .post(this.apiHostUrl + uri, body, { headers: headers })
        .subscribe(
          (res: any) => {
            loading.dismiss();
            resolve(res);
          },
          (err) => {
            loading.dismiss();
            reject(err);
          }
        );
    });
  }

  public hot(count?: number) {
    if (count) return this.setStorage("/hot", count);
    else return this.getStorage("/hot");
  }

  public cool(count?: number) {
    if (count) return this.setStorage("/hot", count);
    else return this.getStorage("/hot");
  }

  public login(credential: any) {
    return this.post("/sso/login", credential);
  }

  public signup(userInfo) {
    return this.post("/sso/signup", userInfo);
  }

  public logout() {
    try {
      this.storage.clear();
    } catch (ex) {
      console.log("logout...");
    }
    this.user = null;
    this.router.navigateByUrl("/auth/login");
  }

  public isEmpty(value: string): boolean {
    if (value == "" || value == null || value == undefined || value == "null") {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 사용자 정보를 Promise<user> 형식으로 스토리지에서 가져오는 함수
   */
  // public getSession(): Promise<any> {

  //     return new Promise((resolve, reject) => {
  //       this.getStorage(Common.USER).then(user => {
  //         if (user && user.jwt) {
  //           this.user = user
  //             resolve(user)
  //         } else {
  //           this.removeStorage(Common.USER)
  //           reject(this.sessionNotFound)
  //         }
  //       }, err => {
  //         console.log(JSON.stringify(err))
  //         reject(this.sessionNotFound)
  //       })
  //     })
  // }

  /**
   * 사용자 정보를 jwt 으로 갱신하는 함수
   */
  public getSession(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.getStorage(Common.USER).then(
        (user) => {
          if (user && user.jwt) {
            this.user = user;
            this.get("/api/v1/user").then(
              (user) => {
                if (user) {
                  this.setStorage(Common.USER, user);
                  resolve(this.user);
                } else {
                  this.removeStorage(Common.USER);
                  reject(this.userNotFound);
                }
              },
              (err) => {
                this.removeStorage(Common.USER);
                reject(this.userNotFound);
              }
            );
          } else {
            this.removeStorage(Common.USER);
            reject(this.userNotFound);
          }
        },
        (err) => {
          console.log(JSON.stringify(err));
          reject(this.userNotFound);
        }
      );
    });
  }

  public navigateRoot(uri: string, data?: any) {
    this.navigate("root", uri, data);
  }

  public navigateForward(uri: string, data?: any) {
    this.navigate("forward", uri, data);
  }

  public navigateBack(data?: any) {
    this.navigate("back", null, data);
  }

  public navigate(direction, uri, data) {
    if (data === undefined || data == null) {
      this.removeStorage(Common.NAVIGATION_EXTRA).then((res) => {
        switch (direction) {
          case "root":
            this.router.navigateByUrl(uri);
            break;
          case "forward":
            this.router.navigateByUrl(uri);
            break;
          case "back":
            this.location.back();
            break;
        }
      });
    } else {
      this.setStorage(Common.NAVIGATION_EXTRA, data).then((extraData) => {
        switch (direction) {
          case "root":
            this.router.navigateByUrl(uri);
            break;
          case "forward":
            this.router.navigateByUrl(uri);
            break;
          case "back":
            this.location.back();
            break;
        }
      });
    }
  }

  public getNavigationExtra() {
    return new Promise((resolve, reject) => {
      this.getStorage(Common.NAVIGATION_EXTRA).then(
        (data) => {
          //this.removeStorage(Common.NAVIGATION_EXTRA)
          resolve(data);
        },
        (err) => {
          reject(err);
        }
      );
    });
  }

  public navigateWithParams(uri, params) {
    let navigationExtras: NavigationExtras = {
      queryParams: params,
    };
    this.setStorage(Common.NAVIGATION_EXTRA, params).then((res) => {
      this.navigate("forward", uri, navigationExtras);
    });
  }

  // public isMobileNumber(str: string): boolean {
  //   return /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/.test(str)
  // }

  // public isValidEmail(str: string): boolean {
  //   return (/^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/.test(str) || /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\-)+\w+\.+\w+$/.test(str))
  // }

  public getImageUrl(id): string {
    return this.apiHostUrl + "api/attachment/image?id=" + id;
  }
}
