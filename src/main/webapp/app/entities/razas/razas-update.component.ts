import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IRazas, Razas } from 'app/shared/model/razas.model';
import { RazasService } from './razas.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEspecies } from 'app/shared/model/especies.model';
import { EspeciesService } from 'app/entities/especies/especies.service';

@Component({
  selector: 'jhi-razas-update',
  templateUrl: './razas-update.component.html'
})
export class RazasUpdateComponent implements OnInit {
  isSaving = false;
  especies: IEspecies[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    decripcion: [],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    especies: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected razasService: RazasService,
    protected especiesService: EspeciesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ razas }) => {
      this.updateForm(razas);

      this.especiesService.query().subscribe((res: HttpResponse<IEspecies[]>) => (this.especies = res.body || []));
    });
  }

  updateForm(razas: IRazas): void {
    this.editForm.patchValue({
      id: razas.id,
      nombre: razas.nombre,
      decripcion: razas.decripcion,
      code: razas.code,
      especies: razas.especies
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('subastasApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const razas = this.createFromForm();
    if (razas.id !== undefined) {
      this.subscribeToSaveResponse(this.razasService.update(razas));
    } else {
      this.subscribeToSaveResponse(this.razasService.create(razas));
    }
  }

  private createFromForm(): IRazas {
    return {
      ...new Razas(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      decripcion: this.editForm.get(['decripcion'])!.value,
      code: this.editForm.get(['code'])!.value,
      especies: this.editForm.get(['especies'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRazas>>): void {
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

  trackById(index: number, item: IEspecies): any {
    return item.id;
  }
}
