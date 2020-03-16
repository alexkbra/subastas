import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDispositivo, Dispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';

@Component({
  selector: 'jhi-dispositivo-update',
  templateUrl: './dispositivo-update.component.html'
})
export class DispositivoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idEvento: [],
    idSubasta: [],
    idLote: [],
    idusuario: [],
    idcliente: [],
    activo: [],
    dispositivo: []
  });

  constructor(protected dispositivoService: DispositivoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dispositivo }) => {
      this.updateForm(dispositivo);
    });
  }

  updateForm(dispositivo: IDispositivo): void {
    this.editForm.patchValue({
      id: dispositivo.id,
      idEvento: dispositivo.idEvento,
      idSubasta: dispositivo.idSubasta,
      idLote: dispositivo.idLote,
      idusuario: dispositivo.idusuario,
      idcliente: dispositivo.idcliente,
      activo: dispositivo.activo,
      dispositivo: dispositivo.dispositivo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dispositivo = this.createFromForm();
    if (dispositivo.id !== undefined) {
      this.subscribeToSaveResponse(this.dispositivoService.update(dispositivo));
    } else {
      this.subscribeToSaveResponse(this.dispositivoService.create(dispositivo));
    }
  }

  private createFromForm(): IDispositivo {
    return {
      ...new Dispositivo(),
      id: this.editForm.get(['id'])!.value,
      idEvento: this.editForm.get(['idEvento'])!.value,
      idSubasta: this.editForm.get(['idSubasta'])!.value,
      idLote: this.editForm.get(['idLote'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      idcliente: this.editForm.get(['idcliente'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      dispositivo: this.editForm.get(['dispositivo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispositivo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
