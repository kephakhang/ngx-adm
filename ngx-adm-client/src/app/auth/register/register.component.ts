import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NbDialogService } from '@nebular/theme'
import { NbAuthSocialLink } from '@nebular/auth/auth.options';
import { RegisterData } from  '../model/register-data'
import { AuthServiceProvider } from '../../providers/auth-service/auth-service';
import { environment } from '../../../environments/environment';
import { Common } from '../../providers/common/common'

@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  isInvalidName = false
  isInvalidEmail = false
  isInvalidMobile = false
  isInvalidPassword = false
  showMessages = {
    error: 'Cannot log in !!!',
    success: 'Login is successful !!'
  };
  errors = [];
  messages = [];
  submitted = false
  enableTerms = true
  isTermsSelected = false
  showPassword = 'password'
  user: RegisterData =  new RegisterData(
    '',
    '',
    '',
    ''
  );
  name = environment.name
  password = environment.password
  confirmPassword = '';
  rememberMe = false;
  socialLinks: NbAuthSocialLink[];
  constructor(public auth: AuthServiceProvider, private cd: ChangeDetectorRef, public dialogService: NbDialogService) { }

  ngOnInit(): void {
    this.auth.getSession().then(user => {
      this.auth.setStorage(Common.USER, user).then(u => {
        this.auth.goHome()
      }) 
    }, err => {
      this.auth.getStorage(Common.REGISTER_USER).then(user => {
        if (user) {
        this.user = user
        }
      })
    })
  }

  public register(): void {

    if (this.isNotValidName()) {
      this.isInvalidName = true
    } else {
      this.isInvalidName = false
    }

    if (this.isNotValidEmail()) {
      this.isInvalidEmail = true
    } else {
      this.isInvalidEmail = false
    }

    if (this.isNotValidMobile()) {
      this.isInvalidMobile = true
    } else {
      this.isInvalidMobile = false
    }

    if (this.isNotValidPassword()) {
      this.isInvalidPassword = true
    } else {
      this.isInvalidPassword = false
    }

    if (this.isInvalidName || this.isInvalidEmail || this.isInvalidMobile || this.isInvalidPassword || this.user.password !== this.confirmPassword) {
      return
    }


    if (this.user.password !== this.confirmPassword) {
      this.auth.presentAlertWithNick('auth.passwordMismatch')
      this.showMessages.error = this.auth.message.get('auth.passwordMismatch')
    } else if (!this.isTermsSelected) {
      this.auth.presentAlertWithNick('auth.register.termsNotSelected')
      this.showMessages.error = this.auth.message.get('auth.register.termsNotSelected')
    } else {
      this.user.mobile = this.user.mobile.replace(/-/g, '')
      this.submitted = true
      this.auth.signup(this.user).then((user: any) => {
        if (user) {
          this.auth.presentAlertWithNick('auth.register.success')
          this.auth.navigateRoot('/auth/login')
        } else {
          this.auth.showError(this.auth.message.get('auth.register.unknownError'))
          this.submitted = false
          this.showMessages.error = this.auth.message.get('auth.register.unknownError')
        }
      }, err => {
        this.auth.showError(err)
        this.submitted = false
        this.showMessages.error = (err instanceof String) ? err as string : this.auth.message.get('auth.register.unknownError')
      })
    }
  }

  public isNotValidName(): boolean {
    if (!this.user.name) return true
    const isValid = this.auth.isValidName(this.user.name)
    if (isValid) {
      this.auth.setStorage(Common.REGISTER_USER, JSON.stringify(this.user as any))
    }
    return !isValid
  }

  public isNotValidMobile(): boolean {
    if (!this.user.mobile) return true
    const isValid = this.auth.isValidMobile(this.user.mobile)
    if (isValid) {
      this.auth.setStorage(Common.REGISTER_USER, this.user)
    }
    return !isValid
  }

  public isNotValidEmail(): boolean {
    if (!this.user.email) return true
    const isValid = this.auth.isValidEmail(this.user.email)
    if (isValid) {
      this.auth.setStorage(Common.REGISTER_USER, this.user)
    }
    return !isValid
  }

  public isNotValidPassword(): boolean {
    if (!this.user.password) return true
    const isValid = this.auth.isValidPassword(this.user.password)
    if (isValid) {
      this.auth.setStorage(Common.REGISTER_USER, this.user)
    }
    return !isValid
  }

  public isNotReadyToSubmit(): boolean {
    if (this.user.password !== this.confirmPassword ||
        this.isNotValidName() || this.isNotValidMobile() ||
        this.isNotValidEmail() || this.isNotValidMobile() || !this.isTermsSelected) {
      return true
    } else {
      return false
    }
  }

  public toggleShowPassword(): void {
    this.showPassword = this.showPassword === 'password' ? 'text' : 'password'
  }
}
