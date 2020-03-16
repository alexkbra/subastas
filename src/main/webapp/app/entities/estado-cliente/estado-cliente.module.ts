import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SubastasSharedModule } from 'app/shared/shared.module';
import { EstadoClienteComponent } from './estado-cliente.component';
import { EstadoClienteDetailComponent } from './estado-cliente-detail.component';
import { EstadoClienteUpdateComponent } from './estado-cliente-update.component';
import { EstadoClienteDeleteDialogComponent } from './estado-cliente-delete-dialog.component';
import { estadoClienteRoute } from './estado-cliente.route';

@NgModule({
  imports: [SubastasSharedModule, RouterModule.forChild(estadoClienteRoute)],
  declarations: [EstadoClienteComponent, EstadoClienteDetailComponent, EstadoClienteUpdateComponent, EstadoClienteDeleteDialogComponent],
  entryComponents: [EstadoClienteDeleteDialogComponent]
})
export class SubastasEstadoClienteModule {}
