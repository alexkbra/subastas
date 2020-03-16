import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { PujadoresComponent } from './pujadores.component';
import { PujadoresDetailComponent } from './pujadores-detail.component';
import { PujadoresUpdateComponent } from './pujadores-update.component';
import { PujadoresDeleteDialogComponent } from './pujadores-delete-dialog.component';
import { pujadoresRoute } from './pujadores.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(pujadoresRoute)],
  declarations: [PujadoresComponent, PujadoresDetailComponent, PujadoresUpdateComponent, PujadoresDeleteDialogComponent],
  entryComponents: [PujadoresDeleteDialogComponent]
})
export class SubastasPujadoresModule {}
