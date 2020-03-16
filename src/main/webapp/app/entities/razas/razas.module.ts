import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { RazasComponent } from './razas.component';
import { RazasDetailComponent } from './razas-detail.component';
import { RazasUpdateComponent } from './razas-update.component';
import { RazasDeleteDialogComponent } from './razas-delete-dialog.component';
import { razasRoute } from './razas.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(razasRoute)],
  declarations: [RazasComponent, RazasDetailComponent, RazasUpdateComponent, RazasDeleteDialogComponent],
  entryComponents: [RazasDeleteDialogComponent]
})
export class SubastasRazasModule {}
