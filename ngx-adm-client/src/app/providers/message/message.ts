import { Injectable } from '@angular/core'
import { Common } from '../common/common'
import { ko } from './i18n/ko';
import { en } from './i18n/en';
import { es } from './i18n/es';
import { de } from './i18n/de';
import { it } from './i18n/it';
import { fr } from './i18n/fr';
import { pt } from './i18n/pt';
import { ja } from './i18n/ja';
import { zh } from './i18n/zh';

@Injectable()
export class Message {

    public lang = 'ko' ;
    public langMapArr = {
        ko: ko,
        en: en,
        de: de,
        es: es,
        fr: fr,
        it: it,
        ja: ja,
        pt: pt,
        zh: zh
    } ;
    constructor() {
    }

    
    public get(nick) {
        if (!nick) return 'Error : null or undefined string '
        const paths: string[] = nick.trim().split('.')
        switch(paths.length) {
            case 1: return this.langMapArr[this.lang][paths[0]] ;
            case 2: return this.langMapArr[this.lang][paths[0]][paths[1]] ;
            case 3: return this.langMapArr[this.lang][paths[0]][paths[1]][paths[2]] ;
            case 4: return this.langMapArr[this.lang][paths[0]][paths[1]][paths[2]][paths[3]] ;
            case 5: return this.langMapArr[this.lang][paths[0]][paths[1]][paths[2]][paths[3]][paths[4]] ;
            case 6: return this.langMapArr[this.lang][paths[0]][paths[1]][paths[2]][paths[3]][paths[4]][paths[5]] ;
            case 7: return this.langMapArr[this.lang][paths[0]][paths[1]][paths[2]][paths[3]][paths[4]][paths[5]][paths[6]] ;
            default: return 'Error : empty or too much long level string'
        }
        
    }
}