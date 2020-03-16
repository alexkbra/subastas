import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';
import { LotesToAnimalesService } from './lotes-to-animales.service';

@Component({
  templateUrl: './lotes-to-animales-delete-dialog.component.html'
})
export class LotesToAnimalesDeleteDialogComponent {
  lotesToAnimales?: ILotesToAnimales;

  constructor(
    protected lotesToAnimalesService: LotesToAnimalesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lotesToAnimalesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lotesToAnimalesListModification');
      this.activeModal.close();
    });
  }
}
