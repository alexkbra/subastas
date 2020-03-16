import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEspecies } from 'app/shared/model/especies.model';
import { EspeciesService } from './especies.service';

@Component({
  templateUrl: './especies-delete-dialog.component.html'
})
export class EspeciesDeleteDialogComponent {
  especies?: IEspecies;

  constructor(protected especiesService: EspeciesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.especiesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('especiesListModification');
      this.activeModal.close();
    });
  }
}
