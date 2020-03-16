import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPujadores } from 'app/shared/model/pujadores.model';
import { PujadoresService } from './pujadores.service';

@Component({
  templateUrl: './pujadores-delete-dialog.component.html'
})
export class PujadoresDeleteDialogComponent {
  pujadores?: IPujadores;

  constructor(protected pujadoresService: PujadoresService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pujadoresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pujadoresListModification');
      this.activeModal.close();
    });
  }
}
