import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubastas } from 'app/shared/model/subastas.model';
import { SubastasService } from './subastas.service';

@Component({
  templateUrl: './subastas-delete-dialog.component.html'
})
export class SubastasDeleteDialogComponent {
  subastas?: ISubastas;

  constructor(protected subastasService: SubastasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subastasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subastasListModification');
      this.activeModal.close();
    });
  }
}
