import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICiudad, Ciudad } from 'app/shared/model/ciudad.model';
import { CiudadService } from './ciudad.service';
import { IMunicipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from 'app/entities/municipios/municipios.service';

@Component({
  selector: 'jhi-ciudad-update',
  templateUrl: './ciudad-update.component.html'
})
export class CiudadUpdateComponent implements OnInit {
  isSaving = false;
  municipios: IMunicipios[] = [];

  editForm = this.fb.group({
    id: [],
    idciudad: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    municipios: [null, Validators.required]
  });

  constructor(
    protected ciudadService: CiudadService,
    protected municipiosService: MunicipiosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ciudad }) => {
      this.updateForm(ciudad);

      this.municipiosService.query().subscribe((res: HttpResponse<IMunicipios[]>) => (this.municipios = res.body || []));
    });
  }

  updateForm(ciudad: ICiudad): void {
    this.editForm.patchValue({
      id: ciudad.id,
      idciudad: ciudad.idciudad,
      nombre: ciudad.nombre,
      municipios: ciudad.municipios
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ciudad = this.createFromForm();
    if (ciudad.id !== undefined) {
      this.subscribeToSaveResponse(this.ciudadService.update(ciudad));
    } else {
      this.subscribeToSaveResponse(this.ciudadService.create(ciudad));
    }
  }

  private createFromForm(): ICiudad {
    return {
      ...new Ciudad(),
      id: this.editForm.get(['id'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      municipios: this.editForm.get(['municipios'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiudad>>): void {
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

  trackById(index: number, item: IMunicipios): any {
    return item.id;
  }
}
