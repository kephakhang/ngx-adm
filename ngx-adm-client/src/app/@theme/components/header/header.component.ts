import { Component, OnDestroy, OnInit, EventEmitter, Output } from '@angular/core';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';

import { UserData } from '../../../@core/data/users';
import { LayoutService } from '../../../@core/utils';
import { map, takeUntil } from 'rxjs/operators';
import { Subject, Observable } from 'rxjs';
import { RippleService } from '../../../@core/utils/ripple.service';
import { AuthServiceProvider } from '../../../providers/auth-service/auth-service';
import { en } from '../../../providers/message/i18n/en';
import { environment } from '../../../../environments/environment';
import { Common } from '../../../providers/common/common';
import { emit } from 'process';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  public readonly materialTheme$: Observable<boolean>;
  userPictureOnly: boolean = false;
  user: any = null
 
  themes = [
    /*
    {
      value: 'default',
      name: 'Light',
    },
    {
      value: 'dark',
      name: 'Dark',
    },
    {
      value: 'cosmic',
      name: 'Cosmic',
    },
    {
      value: 'corporate',
      name: 'Corporate',
    },
    */
    {
      value: 'material-dark',
      name: 'Material Dark',
    },
    {
      value: 'material-light',
      name: 'Material Light',
    },

  ];

  currentTheme = environment.defaultTheme;

  userMenu = [ { title: 'Profile' }, { title: 'Logout', link: '/auth/logout' } ];

  public constructor(
    public auth: AuthServiceProvider,
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private layoutService: LayoutService,
    private breakpointService: NbMediaBreakpointsService,
    private rippleService: RippleService,
  ) {
    this.materialTheme$ = this.auth.theme.onThemeChange()
      .pipe(map(theme => {
        const themeName: string = theme?.name || '';
        return themeName.toLowerCase().startsWith('material');
      }));
  }

  ngOnInit() {
    
    this.auth.getStorage(Common.THEME).then(value => {
      if (value) {
        this.changeTheme = value
      }
    })

    this.auth.getSession().then(user => {
      this.user = user
    }, err => {
      this.auth.logout()
    })

    const { xl } = this.breakpointService.getBreakpointsMap();
    this.auth.theme.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.auth.theme.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => {
        this.currentTheme = themeName;
        this.rippleService.toggle(themeName?.toLowerCase().startsWith('material'));
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.auth.theme.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

}
