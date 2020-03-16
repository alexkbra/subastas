import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMunicipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';

@Component({
  templateUrl: './municipios-delete-dialog.component.html'
})
export class MunicipiosDeleteDialogComponent {
  municipios?: IMunicipios;

  constructor(
    protected municipiosService: MunicipiosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.municipiosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('municipiosListModification');
      this.activeModal.close();
    });
  }
}
