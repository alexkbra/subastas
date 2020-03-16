import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { DispositivoComponent } from './dispositivo.component';
import { DispositivoDetailComponent } from './dispositivo-detail.component';
import { DispositivoUpdateComponent } from './dispositivo-update.component';
import { DispositivoDeleteDialogComponent } from './dispositivo-delete-dialog.component';
import { dispositivoRoute } from './dispositivo.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(dispositivoRoute)],
  declarations: [DispositivoComponent, DispositivoDetailComponent, DispositivoUpdateComponent, DispositivoDeleteDialogComponent],
  entryComponents: [DispositivoDeleteDialogComponent]
})
export class SubastasDispositivoModule {}
