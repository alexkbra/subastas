import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPujas } from 'app/shared/model/pujas.model';
import { PujasService } from './pujas.service';

@Component({
  templateUrl: './pujas-delete-dialog.component.html'
})
export class PujasDeleteDialogComponent {
  pujas?: IPujas;

  constructor(protected pujasService: PujasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pujasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pujasListModification');
      this.activeModal.close();
    });
  }
}
