import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NbCardModule, NbAutocompleteModule } from '@nebular/theme';
import { TenantSearchComponent } from './tenant-search.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NbCardModule,
    NbAutocompleteModule
  ],
  declarations: [
    TenantSearchComponent
  ],
  exports: [
    TenantSearchComponent,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class TenantSearchModule {
}
