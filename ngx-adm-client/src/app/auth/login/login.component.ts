import { Component, OnInit, ChangeDetectorRef } from '@angular/core'
import { Common } from '../../providers/common/common'
import { AuthServiceProvider } from '../../providers/auth-service/auth-service'
import { NbAuthSocialLink } from '@nebular/auth/auth.options'
import { LoginData } from '../model/login-data'
import {
  SocialAuthService,
  GoogleLoginProvider,
  FacebookLoginProvider,
  AmazonLoginProvider,
  MicrosoftLoginProvider,
  SocialUser,
} from 'angularx-social-login'
import { environment } from '../../../environments/environment'


@Component({
  selector: 'nb-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  password = environment.password
  isInvalidId = false
  isInvalidPassword = false
  greeting: string = ''
  idRequired: string = ''
  idShouldBeReal: string = ''
  options: any = {}
  redirectDelay: number
  showMessages = {
    error: 'Cannot log in !!!',
    success: 'Login is successful !!'
  }
  strategy: string
  errors: string[]
  messages: string[]
  user: LoginData = new LoginData(
    '',
    ''
  )
  submitted: boolean = false
  socialLogin: boolean = environment.socialLogin
  socialLinks: NbAuthSocialLink[] = [
    {
      link: '',
      target: '_blank',
      icon: 'google',
      title: 'Google',
    },
    {
      link: '',
      target: '_blank',
      icon: 'facebook',
      title: 'Facebook',
    },
    {
      link: '',
      target: '_blank',
      icon: 'microsoft',
      title: 'Microsoft',
    },
    // {
    //   link: '',
    //   target: '_blank',
    //   icon: 'kakao',
    //   title: 'Kakao',
    // },
    {
      link: '',
      target: '_blank',
      icon: 'apple',
      title: 'Apple',
    },
  ]
  apple: {
    state: 'Initial user authentication request',
    redirectURI: '',
    scope: 'name email mobile',
    color: 'light',
    clientId: 'clientId',
    type: 'sign in',
  }

  constructor(/* private snsAuth: SocialAuthService, */public auth: AuthServiceProvider,  protected cd: ChangeDetectorRef) { }

  ngOnInit(): void {
    if (environment.emailIdOnly) {
      this.greeting = this.auth.message.get('auth.login.greetingEmailOnly')
      this.idShouldBeReal = this.auth.message.get('auth.login.emailShouldBeReal')
      this.idRequired = this.auth.message.get('auth.login.emailRequired')
    } else {
      this.greeting = this.auth.message.get('auth.login.greeting')
      this.idShouldBeReal = this.auth.message.get('auth.login.idShouldBeReal')
      this.idRequired = this.auth.message.get('auth.login.idRequired')
    }

    this.auth.getSession().then(user => {
      this.auth.setStorage(Common.USER, user).then(u => {
        console.log(this.auth.router.url)
        this.auth.getStorage(Common.URI).then(uri => {
          if (uri === '/') {
            this.auth.goHome()
          } else {
            this.auth.navigateRoot(uri)
          }
        }, err => {
          this.auth.goHome()
        })
      }) 
    })
  }

  showPassword = true;

  getInputType() {
    if (this.showPassword) {
      return 'text';
    }
    return 'password';
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
  }

  public isNotValidMobile(): boolean {
    if (!this.user.id) return true
    const isValid = this.auth.isValidMobile(this.user.id)
    return !isValid
  }

  public isNotValidEmail(): boolean {
    if (!this.user.id) return true
    const isValid = this.auth.isValidEmail(this.user.id)
    return !isValid
  }

  public isNotValidId(): boolean {
    const isValid = environment.emailIdOnly ? !this.isNotValidEmail() : !this.isNotValidEmail() || !this.isNotValidMobile()
    // if (isValid) {
    //   this.auth.setStorage(Common.LOGIN_USER, this.user)
    // }
    return !isValid
  }

  public isNotValidPassword(): boolean {
    if (!this.user.password) return true
    const isValid = this.auth.isValidPassword(this.user.password)
    if (isValid) {
      this.auth.setStorage(Common.LOGIN_USER, this.user)
    }
    return !isValid
  }

  public isNotReadyToSubmit(): boolean {
    if (this.isNotValidId() || this.isNotValidPassword()) {
      return true
    } else {
      return false
    }
  }

  login(): void {
    if (this.isNotValidId()) {
      this.isInvalidId = true
    } else {
      this.isInvalidId = false
    }

    if (this.isNotValidPassword()) {
      this.isInvalidPassword = true
    } else {
      this.isInvalidPassword = false
    }

    if (this.isInvalidId || this.isInvalidPassword) {
      return
    }


    this.submitted = true
    this.auth.login(this.user).then((user: any) => {
      this.submitted = false
      if (user) {
        this.auth.user = user
        this.auth.setStorage(Common.USER, user).then(async (value) => {
          await this.auth.removeStorage(Common.TENANTS)
          this.auth.goHome()
        })
        
      } else {
        this.auth.showError('auth.login.unknownError')
        this.submitted = false
        this.showMessages.error = this.auth.message.get('auth.login.unknownError')
      }
    }, err => {
      this.submitted = false
      this.auth.showError(err)
      this.submitted = false
      this.showMessages.error = (err instanceof String) ? err as string : this.auth.message.get('auth.register.unknownError')
    })
  }
/*
  signInWithGoogle():  Promise<SocialUser>  {
    return this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB():  Promise<SocialUser>  {
    return this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signInWithMS():  Promise<SocialUser>  {
    return this.authService.signIn(MicrosoftLoginProvider.PROVIDER_ID)
  }

  signInWithAWS():  Promise<SocialUser>  {
    return this.authService.signIn(AmazonLoginProvider.PROVIDER_ID)
  }
*/
  // signWithKakao():  Promise<SocialUser>  {
  //   return new Promise<SocialUser>((resolve, reject) => {
  //     let loginOptions = {};
  //     loginOptions['authTypes'] = [
  //                                 AuthTypes.AuthTypeTalk,
  //                                 AuthTypes.AuthTypeStory,
  //                                 AuthTypes.AuthTypeAccount
  //                               ];

  //     this.kakao.login(loginOptions).then((res) => {
  //       const user: SocialUser = new SocialUser
  //       user.id = res.id
  //       user.authToken = res.getAccessToken
  //       user.email = res.email
  //       user.name = res.nickname
  //       resolve(user)
  //     }, err => {
  //       reject(err)
  //     })
  //   })
  // }

  snsLogin(sns: NbAuthSocialLink) {
    /*
    switch(sns.title.toLowerCase()) {
      case 'google':
        this.signInWithGoogle()
        break
      case 'microsoft':
        this.signInWithMS()
        break
      case 'amazon':
        this.signInWithAWS()
        break
      case 'facebook':
        this.signInWithFB()
        break
      // case 'kakaotalk':
      //   this.signWithKakao()
      //   break
    }
    */
  }
}
