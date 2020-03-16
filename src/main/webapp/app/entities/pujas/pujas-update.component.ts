import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPujas, Pujas } from 'app/shared/model/pujas.model';
import { PujasService } from './pujas.service';
import { IPujadores } from 'app/shared/model/pujadores.model';
import { PujadoresService } from 'app/entities/pujadores/pujadores.service';

@Component({
  selector: 'jhi-pujas-update',
  templateUrl: './pujas-update.component.html'
})
export class PujasUpdateComponent implements OnInit {
  isSaving = false;
  pujadores: IPujadores[] = [];

  editForm = this.fb.group({
    id: [],
    idEvento: [],
    idSubasta: [],
    idLote: [],
    valor: [null, [Validators.required]],
    fechacreacion: [],
    aceptadoGanador: [],
    pujadores: [null, Validators.required]
  });

  constructor(
    protected pujasService: PujasService,
    protected pujadoresService: PujadoresService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pujas }) => {
      if (!pujas.id) {
        const today = moment().startOf('day');
        pujas.fechacreacion = today;
      }

      this.updateForm(pujas);

      this.pujadoresService.query().subscribe((res: HttpResponse<IPujadores[]>) => (this.pujadores = res.body || []));
    });
  }

  updateForm(pujas: IPujas): void {
    this.editForm.patchValue({
      id: pujas.id,
      idEvento: pujas.idEvento,
      idSubasta: pujas.idSubasta,
      idLote: pujas.idLote,
      valor: pujas.valor,
      fechacreacion: pujas.fechacreacion ? pujas.fechacreacion.format(DATE_TIME_FORMAT) : null,
      aceptadoGanador: pujas.aceptadoGanador,
      pujadores: pujas.pujadores
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pujas = this.createFromForm();
    if (pujas.id !== undefined) {
      this.subscribeToSaveResponse(this.pujasService.update(pujas));
    } else {
      this.subscribeToSaveResponse(this.pujasService.create(pujas));
    }
  }

  private createFromForm(): IPujas {
    return {
      ...new Pujas(),
      id: this.editForm.get(['id'])!.value,
      idEvento: this.editForm.get(['idEvento'])!.value,
      idSubasta: this.editForm.get(['idSubasta'])!.value,
      idLote: this.editForm.get(['idLote'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      aceptadoGanador: this.editForm.get(['aceptadoGanador'])!.value,
      pujadores: this.editForm.get(['pujadores'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPujas>>): void {
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

  trackById(index: number, item: IPujadores): any {
    return item.id;
  }
}
