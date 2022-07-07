import { Component } from '@angular/core'
import { LocalDataSource } from '../../../components/smart-table/public-api'
import { AuthServiceProvider } from '../../../providers/auth-service/auth-service'
import { SmartTableData } from '../../../@core/data/smart-table'
import { UserData } from '../../../model/user-data'
import { TenantData } from 'app/model/tenant-data'
import { HttpParams } from '@angular/common/http'
import { UserLevel } from 'app/enum/user-level'
import { environment } from '../../../../environments/environment'
@Component({
  selector: 'ngx-user-table',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserTableComponent {
  levelMap: Map<Number, String> = new Map([
    [1, "Guest"], 
    [10, "Factory Admin"], 
    [100, "YoungPlusSoft Admin"], 
    [1000, "Super Admin"]
  ])
  userList: UserData[] = []
  tenantList: TenantData[] = []
  levelList: any[] = []

  settings: any = {
      add: {
        addButtonContent: '<i class="nb-plus"></i>',
        createButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
        confirmCreate: false
      },
      edit: {
        editButtonContent: '<i class="nb-edit"></i>',
        saveButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
        confirmSave: true,
      },
      delete: {
        deleteButtonContent: '<i class="nb-trash"></i>',
        confirmDelete: true,
      },
    }

  source: LocalDataSource = new LocalDataSource()

  constructor(public auth: AuthServiceProvider, private service: SmartTableData) {
    this.auth.getSession().then(user => {
      switch(user.level) {
        case UserLevel.Super:
          this.levelList = [ //0: Guest, 10:Factory Admin, 100: YoungPlusSoft Admin, 1000:Super admin',
              {value: 0, title: 'Guest'},
              {value: 10, title: 'Factory Admin'},
              {value: 100, title: 'YoungPlusSoft Admin'},
              {value: 1000, title: 'Super Admin'},
            ]
          break
          case UserLevel.YoungPlusSoft:
            this.levelList = [ //0: Guest, 10:Factory Admin, 100: YoungPlusSoft Admin, 1000:Super admin',
                {value: 0, title: 'Guest'},
                {value: 10, title: 'Factory Admin'},
                {value: 100, title: 'YoungPlusSoft Admin'},
              ]
      }
      this.auth.getTenantList().then(it => {
        this.tenantList = it

        this.settings = {
          add: {
            addButtonContent: '<i class="nb-plus"></i>',
            createButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>',
            confirmCreate: false
          },
          edit: {
            editButtonContent: '<i class="nb-edit"></i>',
            saveButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>',
            confirmSave: true,
          },
          delete: {
            deleteButtonContent: '<i class="nb-trash"></i>',
            confirmDelete: true,
          },
          columns: {
            tenantId: {
              title: 'TenantId',
              type: 'string',
              editor: {
                type: 'completer',
                config: {
                  completer: {
                    data: this.tenantList,
                    searchFields: 'description',
                    titleField: 'id',
                    descriptionField: 'description'
                  }
                }
              }
            },
            name: {
              title: 'Name',
              type: 'string',
            },
            email: {
              title: 'E-mail',
              type: 'string',
              editable: false
            },
            mobile: {
              title: 'Mobile',
              type: 'string',
              editable: false
            },
            level: {
              title: 'Level',
              type: 'number',
              valuePrepareFunction: (value) => {
                return this.levelMap.get(value)
              },
              editor: {
                type: 'list',
                config: {
                  list: this.levelList
                }
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
            }
          },
        }
      

        this.refreshAll()
      }, err => {
        this.auth.showError(err)
      })
      
    }, err => {
      this.auth.showError(err)
    })
    
  }

  refreshAll() {
    this.refresh().then((list: any[]) => {
      this.userList = list.map(item => new UserData(item.id, item.tenantId, item.name, item.detail.email, item.detail.mobile, item.level, new Date(item.regDatetime).toLocaleString(), new Date(item.modDatetime).toLocaleString()))
      this.source.load(this.userList)   
    }, err => {
      this.auth.showError(err)
    })
  }

  refresh() {
    const params = new HttpParams().set('salt', this.auth.getTimeNow())
    return this.auth.get('/api/v1/user/list', params)
  }


  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
     
      const params = new HttpParams().set('id', event.data.id)
      this.auth.delete('/api/v1/user', params).then(res => {
        this.refresh().then((list: any[]) => {
          this.userList = list.map(item => new UserData(item.id, item.tenantId, item.name, item.detail.email, item.detail.mobile, item.level, new Date(item.regDatetime).toLocaleString(), new Date(item.modDatetime).toLocaleString()))
          this.source.load(this.userList)
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

  onSaveConfirm(event): void {
    if (window.confirm('Are you sure you want to update?')) {
     
      this.auth.put('/api/v1/user', event.newData).then(res => {
        this.refresh().then((list: any[]) => {
          this.userList = list.map(item => new UserData(item.id, item.tenantId, item.name, item.detail.email, item.detail.mobile, item.level, new Date(item.regDatetime).toLocaleString(), new Date(item.modDatetime).toLocaleString()))
          this.source.load(this.userList)
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

  onCreateConfirm(event): void {
    if (window.confirm('Are you sure you want to add?')) {
      
      this.auth.post('/api/v1/user', event.data).then(res => {
        this.refresh().then((list: any[]) => {
          this.userList = list.map(item => new UserData(item.id, item.tenantId, item.name, item.detail.email, item.detail.mobile, item.level, new Date(item.regDatetime).toLocaleString(), new Date(item.modDatetime).toLocaleString()))
          this.source.load(this.userList)
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
}
