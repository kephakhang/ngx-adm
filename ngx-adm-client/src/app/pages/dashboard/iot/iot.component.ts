import {Component, OnDestroy } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Common } from '../../../providers/common/common';
import { AuthServiceProvider } from '../../../providers/auth-service/auth-service';

interface CardSettings {
  title: string;
  iconClass: string;
  type: string;
}

@Component({
  selector: 'ngx-iot',
  styleUrls: ['./iot.component.scss'],
  templateUrl: './iot.component.html',
})
export class IotComponent implements OnDestroy {

  private alive = true;

  solarValue: number;
  lightCard: CardSettings = {
    title: 'MMS Server',
    iconClass: 'nb-lightbulb',
    type: 'primary',
  };
  rollerShadesCard: CardSettings = {
    title: 'Ngx Adm Server',
    iconClass: 'nb-roller-shades',
    type: 'success',
  };
  wirelessAudioCard: CardSettings = {
    title: 'Terminal',
    iconClass: 'nb-audio',
    type: 'info',
  };
  coffeeMakerCard: CardSettings = {
    title: 'Counter',
    iconClass: 'nb-coffee-maker',
    type: 'warning',
  };

  statusCards: string;

  commonStatusCardsSet: CardSettings[] = [
    this.lightCard,
    this.rollerShadesCard,
    this.wirelessAudioCard,
    this.coffeeMakerCard,
  ];

  statusCardsByThemes: {
    'material-dark': CardSettings[];
    'material-light': CardSettings[];
  } = {
    'material-dark': this.commonStatusCardsSet,
    'material-light': this.commonStatusCardsSet,
  };

  constructor(public auth: AuthServiceProvider) {
    auth.getStorage(Common.THEME).then(value => {
      this.statusCards = this.statusCardsByThemes[value]
    }, err => {
      this.statusCards = this.statusCardsByThemes[environment.defaultTheme]
    })
    
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
