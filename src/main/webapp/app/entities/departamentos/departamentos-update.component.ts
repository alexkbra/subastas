import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDepartamentos, Departamentos } from 'app/shared/model/departamentos.model';
import { DepartamentosService } from './departamentos.service';

@Component({
  selector: 'jhi-departamentos-update',
  templateUrl: './departamentos-update.component.html'
})
export class DepartamentosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iddepartamentos: [],
    departamento: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255)]]
  });

  constructor(protected departamentosService: DepartamentosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentos }) => {
      this.updateForm(departamentos);
    });
  }

  updateForm(departamentos: IDepartamentos): void {
    this.editForm.patchValue({
      id: departamentos.id,
      iddepartamentos: departamentos.iddepartamentos,
      departamento: departamentos.departamento
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departamentos = this.createFromForm();
    if (departamentos.id !== undefined) {
      this.subscribeToSaveResponse(this.departamentosService.update(departamentos));
    } else {
      this.subscribeToSaveResponse(this.departamentosService.create(departamentos));
    }
  }

  private createFromForm(): IDepartamentos {
    return {
      ...new Departamentos(),
      id: this.editForm.get(['id'])!.value,
      iddepartamentos: this.editForm.get(['iddepartamentos'])!.value,
      departamento: this.editForm.get(['departamento'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamentos>>): void {
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
