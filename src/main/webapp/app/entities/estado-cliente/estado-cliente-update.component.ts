import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoCliente, EstadoCliente } from 'app/shared/model/estado-cliente.model';
import { EstadoClienteService } from './estado-cliente.service';

@Component({
  selector: 'jhi-estado-cliente-update',
  templateUrl: './estado-cliente-update.component.html'
})
export class EstadoClienteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]]
  });

  constructor(protected estadoClienteService: EstadoClienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoCliente }) => {
      this.updateForm(estadoCliente);
    });
  }

  updateForm(estadoCliente: IEstadoCliente): void {
    this.editForm.patchValue({
      id: estadoCliente.id,
      nombre: estadoCliente.nombre,
      code: estadoCliente.code
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoCliente = this.createFromForm();
    if (estadoCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoClienteService.update(estadoCliente));
    } else {
      this.subscribeToSaveResponse(this.estadoClienteService.create(estadoCliente));
    }
  }

  private createFromForm(): IEstadoCliente {
    return {
      ...new EstadoCliente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      code: this.editForm.get(['code'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoCliente>>): void {
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
