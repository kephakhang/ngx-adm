import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NbCardModule, NbIconModule, NbInputModule, NbTreeGridModule,  NbTabsetModule} from '@nebular/theme';
// import { Ng2SmartTableModule } from '../../@theme/components/smart-table/ng2-smart-table.d';
import { Ng2SmartTableModule } from '../../components/smart-table/public-api';
import { Ng2CompleterModule } from 'ng2-completer';
import { ThemeModule } from '../../@theme/theme.module';
import { AdminRoutingModule, routedComponents } from './admin-routing.module';

@NgModule({
  imports: [
    NbCardModule,
    NbTreeGridModule,
    NbIconModule,
    NbInputModule,
    NbTabsetModule,
    ThemeModule,
    AdminRoutingModule,
    Ng2SmartTableModule,
    Ng2CompleterModule
  ],
  declarations: [
    
    ...routedComponents,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
})
export class AdminModule { }
