import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { DepartamentosService } from './departamentos.service';

@Component({
  templateUrl: './departamentos-delete-dialog.component.html'
})
export class DepartamentosDeleteDialogComponent {
  departamentos?: IDepartamentos;

  constructor(
    protected departamentosService: DepartamentosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departamentosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('departamentosListModification');
      this.activeModal.close();
    });
  }
}
