import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';

@Component({
  templateUrl: './contenido-delete-dialog.component.html'
})
export class ContenidoDeleteDialogComponent {
  contenido?: IContenido;

  constructor(protected contenidoService: ContenidoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contenidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contenidoListModification');
      this.activeModal.close();
    });
  }
}
