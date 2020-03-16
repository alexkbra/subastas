import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRazas } from 'app/shared/model/razas.model';
import { RazasService } from './razas.service';

@Component({
  templateUrl: './razas-delete-dialog.component.html'
})
export class RazasDeleteDialogComponent {
  razas?: IRazas;

  constructor(protected razasService: RazasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.razasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('razasListModification');
      this.activeModal.close();
    });
  }
}
