import {Component, OnDestroy, Output, EventEmitter} from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { takeWhile } from 'rxjs/operators' ;
import { SolarData } from '../../@core/data/solar';
import { AuthServiceProvider } from '../../providers/auth-service/auth-service';

interface CardSettings {
  title: string;
  iconClass: string;
  type: string;
}

@Component({
  selector: 'ngx-dashboard',
  template: `<router-outlet></router-outlet>`,
})
export class DashboardComponent {
}
