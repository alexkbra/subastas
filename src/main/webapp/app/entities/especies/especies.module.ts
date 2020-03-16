import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { EspeciesComponent } from './especies.component';
import { EspeciesDetailComponent } from './especies-detail.component';
import { EspeciesUpdateComponent } from './especies-update.component';
import { EspeciesDeleteDialogComponent } from './especies-delete-dialog.component';
import { especiesRoute } from './especies.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(especiesRoute)],
  declarations: [EspeciesComponent, EspeciesDetailComponent, EspeciesUpdateComponent, EspeciesDeleteDialogComponent],
  entryComponents: [EspeciesDeleteDialogComponent]
})
export class SubastasEspeciesModule {}
