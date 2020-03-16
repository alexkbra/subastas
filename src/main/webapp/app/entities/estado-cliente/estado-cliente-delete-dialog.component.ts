import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoCliente } from 'app/shared/model/estado-cliente.model';
import { EstadoClienteService } from './estado-cliente.service';

@Component({
  templateUrl: './estado-cliente-delete-dialog.component.html'
})
export class EstadoClienteDeleteDialogComponent {
  estadoCliente?: IEstadoCliente;

  constructor(
    protected estadoClienteService: EstadoClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoClienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoClienteListModification');
      this.activeModal.close();
    });
  }
}
