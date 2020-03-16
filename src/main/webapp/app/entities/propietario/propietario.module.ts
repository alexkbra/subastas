import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { PropietarioComponent } from './propietario.component';
import { PropietarioDetailComponent } from './propietario-detail.component';
import { PropietarioUpdateComponent } from './propietario-update.component';
import { PropietarioDeleteDialogComponent } from './propietario-delete-dialog.component';
import { propietarioRoute } from './propietario.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(propietarioRoute)],
  declarations: [PropietarioComponent, PropietarioDetailComponent, PropietarioUpdateComponent, PropietarioDeleteDialogComponent],
  entryComponents: [PropietarioDeleteDialogComponent]
})
export class SubastasPropietarioModule {}
