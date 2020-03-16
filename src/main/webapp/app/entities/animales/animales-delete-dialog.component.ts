import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimales } from 'app/shared/model/animales.model';
import { AnimalesService } from './animales.service';

@Component({
  templateUrl: './animales-delete-dialog.component.html'
})
export class AnimalesDeleteDialogComponent {
  animales?: IAnimales;

  constructor(protected animalesService: AnimalesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.animalesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('animalesListModification');
      this.activeModal.close();
    });
  }
}
