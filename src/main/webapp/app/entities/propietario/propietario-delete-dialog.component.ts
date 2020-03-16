import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropietario } from 'app/shared/model/propietario.model';
import { PropietarioService } from './propietario.service';

@Component({
  templateUrl: './propietario-delete-dialog.component.html'
})
export class PropietarioDeleteDialogComponent {
  propietario?: IPropietario;

  constructor(
    protected propietarioService: PropietarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.propietarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('propietarioListModification');
      this.activeModal.close();
    });
  }
}
