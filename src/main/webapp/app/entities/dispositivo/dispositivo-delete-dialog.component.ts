import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';

@Component({
  templateUrl: './dispositivo-delete-dialog.component.html'
})
export class DispositivoDeleteDialogComponent {
  dispositivo?: IDispositivo;

  constructor(
    protected dispositivoService: DispositivoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dispositivoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dispositivoListModification');
      this.activeModal.close();
    });
  }
}
