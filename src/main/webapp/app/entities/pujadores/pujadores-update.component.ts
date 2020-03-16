import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPujadores, Pujadores } from 'app/shared/model/pujadores.model';
import { PujadoresService } from './pujadores.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-pujadores-update',
  templateUrl: './pujadores-update.component.html'
})
export class PujadoresUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    idEvento: [],
    idSubasta: [],
    idLote: [],
    nroconsignacion: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    nombrebanco: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    estado: [],
    pagoAceptado: [],
    cliente: [null, Validators.required]
  });

  constructor(
    protected pujadoresService: PujadoresService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pujadores }) => {
      this.updateForm(pujadores);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(pujadores: IPujadores): void {
    this.editForm.patchValue({
      id: pujadores.id,
      idEvento: pujadores.idEvento,
      idSubasta: pujadores.idSubasta,
      idLote: pujadores.idLote,
      nroconsignacion: pujadores.nroconsignacion,
      nombrebanco: pujadores.nombrebanco,
      estado: pujadores.estado,
      pagoAceptado: pujadores.pagoAceptado,
      cliente: pujadores.cliente
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pujadores = this.createFromForm();
    if (pujadores.id !== undefined) {
      this.subscribeToSaveResponse(this.pujadoresService.update(pujadores));
    } else {
      this.subscribeToSaveResponse(this.pujadoresService.create(pujadores));
    }
  }

  private createFromForm(): IPujadores {
    return {
      ...new Pujadores(),
      id: this.editForm.get(['id'])!.value,
      idEvento: this.editForm.get(['idEvento'])!.value,
      idSubasta: this.editForm.get(['idSubasta'])!.value,
      idLote: this.editForm.get(['idLote'])!.value,
      nroconsignacion: this.editForm.get(['nroconsignacion'])!.value,
      nombrebanco: this.editForm.get(['nombrebanco'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      pagoAceptado: this.editForm.get(['pagoAceptado'])!.value,
      cliente: this.editForm.get(['cliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPujadores>>): void {
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

  trackById(index: number, item: ICliente): any {
    return item.id;
  }
}
