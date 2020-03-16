import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventos } from 'app/shared/model/eventos.model';
import { EventosService } from './eventos.service';

@Component({
  templateUrl: './eventos-delete-dialog.component.html'
})
export class EventosDeleteDialogComponent {
  eventos?: IEventos;

  constructor(protected eventosService: EventosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventosListModification');
      this.activeModal.close();
    });
  }
}
