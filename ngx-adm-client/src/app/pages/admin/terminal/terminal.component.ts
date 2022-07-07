import { Component } from "@angular/core";
import { LocalDataSource } from "../../../components/smart-table/public-api";
import { AuthServiceProvider } from "../../../providers/auth-service/auth-service";
import { SmartTableData } from "../../../@core/data/smart-table";
import { TerminalData } from "../../../model/terminal-data";
import { TenantData } from "../../../model/tenant-data";
import { HttpParams } from "@angular/common/http";
import { Common } from "../../../providers/common/common";
import { CompleterService, CompleterData } from "ng2-completer";
import { environment } from "../../../../environments/environment";

@Component({
  selector: "ngx-terminal-table",
  templateUrl: "./terminal.component.html",
  styleUrls: ["./terminal.component.scss"],
})
export class TerminalTableComponent {
  statusMap: Map<Number, String> = new Map([
    [0, "Avaiable"],
    [1, "Installed"],
    [2, "Discarded"],
    [3, "Detached"],
  ]);
  terminalList: TerminalData[] = [];
  tenantList: TenantData[] = [];
  tenantId: string = "";
  dataService: CompleterData;

  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
      confirmCreate: true,
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

            this.dataService = this.completerService.local(
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
            this.auth.showError(err);
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
      (list: TerminalData[]) => {
        this.terminalList = list;
        this.source.load(this.terminalList);
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
    return this.auth.get("/api/v1/terminal/list", params);
  }

  onDeleteConfirm(event): void {
    if (window.confirm("Are you sure you want to delete?")) {
      const params = new HttpParams().set("id", event.data.id);
      this.auth.delete("/api/v1/terminal", params).then(
        (res) => {
          this.refresh(this.tenantId).then(
            (list: TerminalData[]) => {
              this.terminalList = list;
              this.source.load(this.terminalList);
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
      this.auth.put("/api/v1/terminal", event.newData).then(
        (res) => {
          this.refresh(this.tenantId).then(
            (list: TerminalData[]) => {
              this.terminalList = list;
              this.source.load(this.terminalList);
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

  onCreateConfirm(event): void {
    if (window.confirm("Are you sure you want to add?")) {
      event.newData.tenantId = this.tenantId;
      this.auth.post("/api/v1/terminal", event.newData).then(
        (res) => {
          event.confirm.resolve();
        },
        (err) => {
          this.auth.showError(err);
          this.source.remove(event.newData);
          event.confirm.reject();
        }
      );
    } else {
      event.confirm.reject();
    }
  }
}
