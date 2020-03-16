import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { AnimalesComponent } from './animales.component';
import { AnimalesDetailComponent } from './animales-detail.component';
import { AnimalesUpdateComponent } from './animales-update.component';
import { AnimalesDeleteDialogComponent } from './animales-delete-dialog.component';
import { animalesRoute } from './animales.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(animalesRoute)],
  declarations: [AnimalesComponent, AnimalesDetailComponent, AnimalesUpdateComponent, AnimalesDeleteDialogComponent],
  entryComponents: [AnimalesDeleteDialogComponent]
})
export class SubastasAnimalesModule {}
