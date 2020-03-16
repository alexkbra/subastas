import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMensajes } from 'app/shared/model/mensajes.model';
import { MensajesService } from './mensajes.service';

@Component({
  templateUrl: './mensajes-delete-dialog.component.html'
})
export class MensajesDeleteDialogComponent {
  mensajes?: IMensajes;

  constructor(protected mensajesService: MensajesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mensajesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mensajesListModification');
      this.activeModal.close();
    });
  }
}
