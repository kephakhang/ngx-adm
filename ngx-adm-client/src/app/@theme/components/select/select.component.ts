import { Component, Input, AfterViewInit } from '@angular/core'
import { DefaultEditor } from '../../../components/smart-table/public-api'
import { SelectData } from '../model/select-data'

@Component({
  selector: 'ngx-select',
  styleUrls: ['./select.component.scss'],
  template: `
    <select>
        <nb-option *ngFor="let option of options"
            value="{{ option.value}}"
            >
            {{option.name}}
        </nb-option>
    </select>
  `,
})
export class SelectComponent{
  options = []

}