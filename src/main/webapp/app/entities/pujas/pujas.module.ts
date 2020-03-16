import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { PujasComponent } from './pujas.component';
import { PujasDetailComponent } from './pujas-detail.component';
import { PujasUpdateComponent } from './pujas-update.component';
import { PujasDeleteDialogComponent } from './pujas-delete-dialog.component';
import { pujasRoute } from './pujas.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(pujasRoute)],
  declarations: [PujasComponent, PujasDetailComponent, PujasUpdateComponent, PujasDeleteDialogComponent],
  entryComponents: [PujasDeleteDialogComponent]
})
export class SubastasPujasModule {}
