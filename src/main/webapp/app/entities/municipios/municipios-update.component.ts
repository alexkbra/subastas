import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMunicipios, Municipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';
import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { DepartamentosService } from 'app/entities/departamentos/departamentos.service';

@Component({
  selector: 'jhi-municipios-update',
  templateUrl: './municipios-update.component.html'
})
export class MunicipiosUpdateComponent implements OnInit {
  isSaving = false;
  departamentos: IDepartamentos[] = [];

  editForm = this.fb.group({
    id: [],
    idmunicipios: [],
    municipio: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    estado: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    departamentos: [null, Validators.required]
  });

  constructor(
    protected municipiosService: MunicipiosService,
    protected departamentosService: DepartamentosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ municipios }) => {
      this.updateForm(municipios);

      this.departamentosService.query().subscribe((res: HttpResponse<IDepartamentos[]>) => (this.departamentos = res.body || []));
    });
  }

  updateForm(municipios: IMunicipios): void {
    this.editForm.patchValue({
      id: municipios.id,
      idmunicipios: municipios.idmunicipios,
      municipio: municipios.municipio,
      estado: municipios.estado,
      departamentos: municipios.departamentos
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const municipios = this.createFromForm();
    if (municipios.id !== undefined) {
      this.subscribeToSaveResponse(this.municipiosService.update(municipios));
    } else {
      this.subscribeToSaveResponse(this.municipiosService.create(municipios));
    }
  }

  private createFromForm(): IMunicipios {
    return {
      ...new Municipios(),
      id: this.editForm.get(['id'])!.value,
      idmunicipios: this.editForm.get(['idmunicipios'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      departamentos: this.editForm.get(['departamentos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipios>>): void {
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

  trackById(index: number, item: IDepartamentos): any {
    return item.id;
  }
}
