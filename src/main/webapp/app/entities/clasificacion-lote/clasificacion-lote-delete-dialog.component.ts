import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';
import { ClasificacionLoteService } from './clasificacion-lote.service';

@Component({
  templateUrl: './clasificacion-lote-delete-dialog.component.html'
})
export class ClasificacionLoteDeleteDialogComponent {
  clasificacionLote?: IClasificacionLote;

  constructor(
    protected clasificacionLoteService: ClasificacionLoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clasificacionLoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clasificacionLoteListModification');
      this.activeModal.close();
    });
  }
}
