import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { EventosComponent } from './eventos.component';
import { EventosDetailComponent } from './eventos-detail.component';
import { EventosUpdateComponent } from './eventos-update.component';
import { EventosDeleteDialogComponent } from './eventos-delete-dialog.component';
import { eventosRoute } from './eventos.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(eventosRoute)],
  declarations: [EventosComponent, EventosDetailComponent, EventosUpdateComponent, EventosDeleteDialogComponent],
  entryComponents: [EventosDeleteDialogComponent]
})
export class SubastasEventosModule {}
