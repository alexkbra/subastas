import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { ClasificacionLoteComponent } from './clasificacion-lote.component';
import { ClasificacionLoteDetailComponent } from './clasificacion-lote-detail.component';
import { ClasificacionLoteUpdateComponent } from './clasificacion-lote-update.component';
import { ClasificacionLoteDeleteDialogComponent } from './clasificacion-lote-delete-dialog.component';
import { clasificacionLoteRoute } from './clasificacion-lote.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(clasificacionLoteRoute)],
  declarations: [
    ClasificacionLoteComponent,
    ClasificacionLoteDetailComponent,
    ClasificacionLoteUpdateComponent,
    ClasificacionLoteDeleteDialogComponent
  ],
  entryComponents: [ClasificacionLoteDeleteDialogComponent]
})
export class SubastasClasificacionLoteModule {}
