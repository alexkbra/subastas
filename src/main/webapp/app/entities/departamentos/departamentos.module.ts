import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { DepartamentosComponent } from './departamentos.component';
import { DepartamentosDetailComponent } from './departamentos-detail.component';
import { DepartamentosUpdateComponent } from './departamentos-update.component';
import { DepartamentosDeleteDialogComponent } from './departamentos-delete-dialog.component';
import { departamentosRoute } from './departamentos.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(departamentosRoute)],
  declarations: [DepartamentosComponent, DepartamentosDetailComponent, DepartamentosUpdateComponent, DepartamentosDeleteDialogComponent],
  entryComponents: [DepartamentosDeleteDialogComponent]
})
export class SubastasDepartamentosModule {}
