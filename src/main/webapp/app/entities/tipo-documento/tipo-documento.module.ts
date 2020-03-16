import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { TipoDocumentoComponent } from './tipo-documento.component';
import { TipoDocumentoDetailComponent } from './tipo-documento-detail.component';
import { TipoDocumentoUpdateComponent } from './tipo-documento-update.component';
import { TipoDocumentoDeleteDialogComponent } from './tipo-documento-delete-dialog.component';
import { tipoDocumentoRoute } from './tipo-documento.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(tipoDocumentoRoute)],
  declarations: [TipoDocumentoComponent, TipoDocumentoDetailComponent, TipoDocumentoUpdateComponent, TipoDocumentoDeleteDialogComponent],
  entryComponents: [TipoDocumentoDeleteDialogComponent]
})
export class SubastasTipoDocumentoModule {}
