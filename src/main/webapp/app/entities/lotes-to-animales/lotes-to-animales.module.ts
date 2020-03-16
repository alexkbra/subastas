import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { LotesToAnimalesComponent } from './lotes-to-animales.component';
import { LotesToAnimalesDetailComponent } from './lotes-to-animales-detail.component';
import { LotesToAnimalesUpdateComponent } from './lotes-to-animales-update.component';
import { LotesToAnimalesDeleteDialogComponent } from './lotes-to-animales-delete-dialog.component';
import { lotesToAnimalesRoute } from './lotes-to-animales.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(lotesToAnimalesRoute)],
  declarations: [
    LotesToAnimalesComponent,
    LotesToAnimalesDetailComponent,
    LotesToAnimalesUpdateComponent,
    LotesToAnimalesDeleteDialogComponent
  ],
  entryComponents: [LotesToAnimalesDeleteDialogComponent]
})
export class SubastasLotesToAnimalesModule {}
