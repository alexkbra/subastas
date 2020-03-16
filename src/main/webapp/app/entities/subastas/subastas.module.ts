import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { SubastasComponent } from './subastas.component';
import { SubastasDetailComponent } from './subastas-detail.component';
import { SubastasUpdateComponent } from './subastas-update.component';
import { SubastasDeleteDialogComponent } from './subastas-delete-dialog.component';
import { subastasRoute } from './subastas.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(subastasRoute)],
  declarations: [SubastasComponent, SubastasDetailComponent, SubastasUpdateComponent, SubastasDeleteDialogComponent],
  entryComponents: [SubastasDeleteDialogComponent]
})
export class SubastasSubastasModule {}
