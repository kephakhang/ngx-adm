import { Component, OnInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core'
import { Observable, of } from 'rxjs'
import { FormControl } from '@angular/forms'
import { map, startWith } from 'rxjs/operators'
import { TenantData } from '../../model/tenant-data'
import { AuthServiceProvider } from '../../providers/auth-service/auth-service'

@Component({
  selector: 'ngx-tenant-search',
  templateUrl: './tenant-search.component.html',
  styleUrls: ['./tenant-search.component.scss']
})
export class TenantSearchComponent implements OnInit {
  tenantList: TenantData[] = []
  filteredOptions$: Observable<TenantData[]>;
  inputFormControl: FormControl;

  @Output() change = new EventEmitter<any>()

  constructor(public auth: AuthServiceProvider) { 
    this.refresh()
  }

  refresh() {
    this.auth.getTenantList().then((list: TenantData[]) => {
      this.tenantList = list
      this.filteredOptions$ = of(this.tenantList)
    }, err => {
      this.auth.showError(err)
    })
  }


  ngOnInit() {

    this.inputFormControl = new FormControl();

    this.filteredOptions$ = this.inputFormControl.valueChanges
      .pipe(
        startWith(''),
        map(filterString => this.filter(filterString)),
      );

  }

  private filter(value: string): TenantData[] {
    const filterValue = value.toLowerCase()
    return this.tenantList.filter(item => item.description.toLowerCase().includes(filterValue))
  }
  
}
