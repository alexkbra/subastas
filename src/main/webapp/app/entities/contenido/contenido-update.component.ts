import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-contenido-update',
  templateUrl: './contenido-update.component.html'
})
export class ContenidoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [],
    url: [null, [Validators.maxLength(500)]],
    imagenUrl: [],
    imagenUrlContentType: [],
    texto: [],
    entidad: [],
    idEntidad: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contenidoService: ContenidoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenido }) => {
      this.updateForm(contenido);
    });
  }

  updateForm(contenido: IContenido): void {
    this.editForm.patchValue({
      id: contenido.id,
      tipo: contenido.tipo,
      url: contenido.url,
      imagenUrl: contenido.imagenUrl,
      imagenUrlContentType: contenido.imagenUrlContentType,
      texto: contenido.texto,
      entidad: contenido.entidad,
      idEntidad: contenido.idEntidad
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
    const contenido = this.createFromForm();
    if (contenido.id !== undefined) {
      this.subscribeToSaveResponse(this.contenidoService.update(contenido));
    } else {
      this.subscribeToSaveResponse(this.contenidoService.create(contenido));
    }
  }

  private createFromForm(): IContenido {
    return {
      ...new Contenido(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      url: this.editForm.get(['url'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      texto: this.editForm.get(['texto'])!.value,
      entidad: this.editForm.get(['entidad'])!.value,
      idEntidad: this.editForm.get(['idEntidad'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenido>>): void {
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
