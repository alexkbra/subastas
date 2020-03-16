import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClasificacionLote, ClasificacionLote } from 'app/shared/model/clasificacion-lote.model';
import { ClasificacionLoteService } from './clasificacion-lote.service';

@Component({
  selector: 'jhi-clasificacion-lote-update',
  templateUrl: './clasificacion-lote-update.component.html'
})
export class ClasificacionLoteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]]
  });

  constructor(
    protected clasificacionLoteService: ClasificacionLoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clasificacionLote }) => {
      this.updateForm(clasificacionLote);
    });
  }

  updateForm(clasificacionLote: IClasificacionLote): void {
    this.editForm.patchValue({
      id: clasificacionLote.id,
      nombre: clasificacionLote.nombre,
      code: clasificacionLote.code
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clasificacionLote = this.createFromForm();
    if (clasificacionLote.id !== undefined) {
      this.subscribeToSaveResponse(this.clasificacionLoteService.update(clasificacionLote));
    } else {
      this.subscribeToSaveResponse(this.clasificacionLoteService.create(clasificacionLote));
    }
  }

  private createFromForm(): IClasificacionLote {
    return {
      ...new ClasificacionLote(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      code: this.editForm.get(['code'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasificacionLote>>): void {
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
