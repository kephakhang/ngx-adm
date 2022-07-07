import { Component, Output, EventEmitter } from '@angular/core'
import { LocalDataSource } from '../../../components/smart-table/public-api'
import { AuthServiceProvider } from '../../../providers/auth-service/auth-service'
import { SmartTableData } from '../../../@core/data/smart-table'
import { TenantData } from '../../../model/tenant-data'
import { HttpParams } from '@angular/common/http'
import { Common } from 'app/providers/common/common'
import { environment } from '../../../../environments/environment'

@Component({
  selector: 'ngx-tenant-table',
  templateUrl: './tenant.component.html',
  styleUrls: ['./tenant.component.scss'],
})
export class TenantTableComponent {

  tenantList: TenantData[]

  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
      confirmCreate: true
    },
    edit: {
      editButtonContent: '<i class="nb-edit"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
      confirmSave: true
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash"></i>',
      confirmDelete: true,
    },
    columns: {
      id: {
        title: 'Id',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      type: {
        title: 'Type',
        type: 'number',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
      countryCode: {
        title: 'Country',
        type: 'string',
      },
      hostUrl: {
        title: 'Host Url',
        type: 'string',
      },
      prefix: {
        title: 'Prefix',
        type: 'string',
      },
      hostname: {
        title: 'Hostname',
        type: 'string',
      },
      enable: {
        title: 'enable',
        type: 'boolean',
        config: {
          list: [ 
            {value: false, title: "Enable"}, 
            {value: true, title: "Disable"}
          ]
        }
      },
      regDatetime: {
        title: 'Registered',
        type: 'string',
        editable: false
      },
      modDatetime: {
        title: 'Updated',
        type: 'string',
        editable: false
      },
    },
  }

  source: LocalDataSource = new LocalDataSource()

  constructor(public auth: AuthServiceProvider, private service: SmartTableData) {
    this.auth.getSession().then(user => {
      this.refreshAll()
    }, err => {
      this.auth.showError(err)
    })
    
  }

  refreshAll() {
    this.refresh().then(async (list: any[]) => {
      this.tenantList = list.map(item => new TenantData(item.id, item.name, item.type, item.description, item.countryCode, item.hostUrl, item.prefix, item.hostname, new Date(item.regDatetime).toLocaleString(), new Date(item.modDatetime).toLocaleString()))      
      await this.auth.setStorage(Common.TENANTS, this.tenantList)
      this.source.load(this.tenantList)
    }, err => {
      this.auth.showError(err)
    })
  }

  refresh() {
    const params = new HttpParams().set('salt', this.auth.getTimeNow())
    return this.auth.get('/api/v1/tenant/list', params)
  }


  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve()
      const params = new HttpParams().set('id', event.data.id)
      this.auth.delete('/api/v1/tenant', params).then(res => {
        this.refresh().then((list: TenantData[]) => {
          this.tenantList = list
          this.source.load(this.tenantList)
          event.confirm.resolve()
        }, err => {
          this.auth.showError(err)
          event.confirm.reject()
        })
      }, err => {
        this.auth.showError(err)
        event.confirm.reject()
      })
    } else {
      event.confirm.reject()
    }
  }

  onEditConfirm(event): void {
    if (window.confirm('Are you sure you want to update?')) {
      event.confirm.resolve()
      this.auth.put('/api/v1/tenant', event.data).then(res => {
        this.refresh().then((list: TenantData[]) => {
          this.tenantList = list
          this.source.load(this.tenantList)
          event.confirm.resolve()
        }, err => {
          this.auth.showError(err)
          event.confirm.reject()
        })
      }, err => {
        this.auth.showError(err)
      })
    } else {
      event.confirm.reject()
    }
  }

  onCreateConfirm(event): void {
    if (window.confirm('Are you sure you want to add?')) {
      event.confirm.resolve()
      this.auth.post('/api/v1/tenant', event.data).then(res => {
        this.refresh().then((list: TenantData[]) => {
          this.tenantList = list
          this.source.load(this.tenantList)
          event.confirm.resolve()
        }, err => {
          this.auth.showError(err)
          event.confirm.reject()
        })
      }, err => {
        this.auth.showError(err)
      })
    } else {
      event.confirm.reject()
    }
  }
}
