import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnimales, Animales } from 'app/shared/model/animales.model';
import { AnimalesService } from './animales.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IRazas } from 'app/shared/model/razas.model';
import { RazasService } from 'app/entities/razas/razas.service';

@Component({
  selector: 'jhi-animales-update',
  templateUrl: './animales-update.component.html'
})
export class AnimalesUpdateComponent implements OnInit {
  isSaving = false;
  razas: IRazas[] = [];

  editForm = this.fb.group({
    id: [],
    pesokg: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    sexo: [],
    procedencia: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    propietario: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    imagenUrl: [],
    imagenUrlContentType: [],
    videoUrl: [null, [Validators.maxLength(500)]],
    razas: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected animalesService: AnimalesService,
    protected razasService: RazasService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ animales }) => {
      this.updateForm(animales);

      this.razasService.query().subscribe((res: HttpResponse<IRazas[]>) => (this.razas = res.body || []));
    });
  }

  updateForm(animales: IAnimales): void {
    this.editForm.patchValue({
      id: animales.id,
      pesokg: animales.pesokg,
      descripcion: animales.descripcion,
      sexo: animales.sexo,
      procedencia: animales.procedencia,
      propietario: animales.propietario,
      imagenUrl: animales.imagenUrl,
      imagenUrlContentType: animales.imagenUrlContentType,
      videoUrl: animales.videoUrl,
      razas: animales.razas
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const animales = this.createFromForm();
    if (animales.id !== undefined) {
      this.subscribeToSaveResponse(this.animalesService.update(animales));
    } else {
      this.subscribeToSaveResponse(this.animalesService.create(animales));
    }
  }

  private createFromForm(): IAnimales {
    return {
      ...new Animales(),
      id: this.editForm.get(['id'])!.value,
      pesokg: this.editForm.get(['pesokg'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      sexo: this.editForm.get(['sexo'])!.value,
      procedencia: this.editForm.get(['procedencia'])!.value,
      propietario: this.editForm.get(['propietario'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      razas: this.editForm.get(['razas'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimales>>): void {
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

  trackById(index: number, item: IRazas): any {
    return item.id;
  }
}
