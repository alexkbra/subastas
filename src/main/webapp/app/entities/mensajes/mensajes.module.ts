import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { MensajesComponent } from './mensajes.component';
import { MensajesDetailComponent } from './mensajes-detail.component';
import { MensajesUpdateComponent } from './mensajes-update.component';
import { MensajesDeleteDialogComponent } from './mensajes-delete-dialog.component';
import { mensajesRoute } from './mensajes.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(mensajesRoute)],
  declarations: [MensajesComponent, MensajesDetailComponent, MensajesUpdateComponent, MensajesDeleteDialogComponent],
  entryComponents: [MensajesDeleteDialogComponent]
})
export class SubastasMensajesModule {}
