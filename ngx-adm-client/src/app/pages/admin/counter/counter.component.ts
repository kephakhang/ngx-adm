import { Component } from "@angular/core";
import { LocalDataSource } from "../../../components/smart-table/public-api";
import { AuthServiceProvider } from "../../../providers/auth-service/auth-service";
import { SmartTableData } from "../../../@core/data/smart-table";
import { CounterData } from "../../../model/counter-data";
import { TenantData } from "../../../model/tenant-data";
import { NbTabComponent } from "@nebular/theme";
import { HttpParams } from "@angular/common/http";
import { Common } from "../../../providers/common/common";
import { CompleterService, CompleterData } from "ng2-completer";
import { environment } from "../../../../environments/environment";

@Component({
  selector: "ngx-counter-table",
  templateUrl: "./counter.component.html",
  styleUrls: ["./counter.component.scss"],
})
export class CounterTableComponent {
  statusMap: Map<Number, String> = new Map([
    [0, "Avaiable"],
    [1, "Installed"],
    [2, "Discarded"],
    [3, "Detached"],
  ]);
  counterList: CounterData[] = [];
  tenantList: TenantData[] = [];
  tenantId: string = "";
  dataService: CompleterData;

  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
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
      id: {
        title: "Id",
        type: "string",
        editable: false,
      },
      tenantId: {
        title: "Tenant",
        type: "string",
        editable: false,
        addable: false,
      },
      terminalId: {
        title: "Terminal",
        type: "string",
      },
      version: {
        title: "Version",
        type: "number",
      },
      status: {
        title: "Status",
        type: "number",
        valuePrepareFunction: (value) => {
          return this.statusMap.get(value);
        },
        editor: {
          type: "list",
          config: {
            list: [
              { value: 0, title: "Avaiable" },
              { value: 1, title: "Installed" },
              { value: 2, title: "Discarded" },
              { value: 3, title: "Detached" },
            ],
          },
        },
      },
      regDatetime: {
        title: "Registered",
        type: "string",
        editable: false,
        addable: false,
      },
      modDatetime: {
        title: "Updated",
        type: "string",
        editable: false,
        addable: false,
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(
    public auth: AuthServiceProvider,
    private service: SmartTableData,
    private completerService: CompleterService
  ) {
    this.auth.getSession().then(
      (user) => {
        this.auth.getTenantList().then(
          (list: TenantData[]) => {
            this.tenantList = list;

            this.dataService = completerService.local(
              this.tenantList,
              "description",
              "id"
            );

            this.auth.getStorage(Common.TENANT_ID).then(
              (it) => {
                if (it) {
                  this.refreshAll(it);
                } else {
                  this.refreshAll(this.tenantList[0].id);
                }
              },
              (err) => {
                this.refreshAll(this.tenantList[0].id);
              }
            );
          },
          (err) => {
            this.tenantId = this.tenantList[0].id;
            this.refreshAll(this.tenantId);
          }
        );
      },
      (err) => {
        this.auth.showError(err);
      }
    );
  }

  onSelectedChange(event) {
    this.refreshAll(event.title);
  }

  refreshAll(tenantId: string) {
    this.refresh(tenantId).then(
      (list: any[]) => {
        this.counterList = list.map(
          (item) =>
            new CounterData(
              item.id,
              item.tenantId,
              item.terminalId,
              item.version,
              item.status,
              new Date(item.regDatetime).toLocaleString(),
              new Date(item.modDatetime).toLocaleString()
            )
        );
        this.source.load(this.counterList);
      },
      (err) => {
        this.auth.showError(err);
      }
    );
  }

  refresh(tenantId: string) {
    this.tenantId = tenantId;
    this.auth.setStorage(Common.TENANT_ID, this.tenantId);
    const params = new HttpParams()
      .set("tenantId", tenantId)
      .set("salt", this.auth.getTimeNow());
    return this.auth.get("/api/v1/counter/list", params);
  }

  onDeleteConfirm(event): void {
    if (window.confirm("Are you sure you want to delete?")) {
      const params = new HttpParams().set("id", event.data.id);
      this.auth.delete("/api/v1/counter", params).then(
        (res) => {
          this.refresh(this.tenantId).then(
            (list: any[]) => {
              this.counterList = list.map(
                (item) =>
                  new CounterData(
                    item.id,
                    item.tenantId,
                    item.terminalId,
                    item.version,
                    item.status,
                    new Date(item.regDatetime).toLocaleString(),
                    new Date(item.modDatetime).toLocaleString()
                  )
              );
              this.source.load(this.counterList);
              event.confirm.resolve();
            },
            (err) => {
              this.auth.showError(err);
              event.confirm.reject();
            }
          );
        },
        (err) => {
          this.auth.showError(err);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }

  onSaveConfirm(event): void {
    if (window.confirm("Are you sure you want to update?")) {
      event.newData.tenantId = this.tenantId;
      this.auth.put("/api/v1/counter", event.newData).then(
        (res) => {
          this.refresh(this.tenantId).then(
            (list: any[]) => {
              this.counterList = list.map(
                (item) =>
                  new CounterData(
                    item.id,
                    item.tenantId,
                    item.terminalId,
                    item.version,
                    item.status,
                    new Date(item.regDatetime).toLocaleString(),
                    new Date(item.modDatetime).toLocaleString()
                  )
              );
              this.source.load(this.counterList);
              event.confirm.resolve();
            },
            (err) => {
              this.auth.showError(err);
              event.confirm.reject();
            }
          );
        },
        (err) => {
          this.auth.showError(err);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }
}
