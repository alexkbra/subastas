import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEventos, Eventos } from 'app/shared/model/eventos.model';
import { EventosService } from './eventos.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-eventos-update',
  templateUrl: './eventos-update.component.html'
})
export class EventosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    decripcion: [],
    fechainicio: [null, [Validators.required]],
    fechafinal: [null, [Validators.required]],
    fechacreacion: [null, [Validators.required]],
    imagenUrl: [null, [Validators.required]],
    imagenUrlContentType: [],
    videoUrl: [null, [Validators.maxLength(500)]],
    idciudad: [],
    latitud: [],
    longitud: [],
    estadoActivo: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected eventosService: EventosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventos }) => {
      if (!eventos.id) {
        const today = moment().startOf('day');
        eventos.fechainicio = today;
        eventos.fechafinal = today;
        eventos.fechacreacion = today;
      }

      this.updateForm(eventos);
    });
  }

  updateForm(eventos: IEventos): void {
    this.editForm.patchValue({
      id: eventos.id,
      nombre: eventos.nombre,
      decripcion: eventos.decripcion,
      fechainicio: eventos.fechainicio ? eventos.fechainicio.format(DATE_TIME_FORMAT) : null,
      fechafinal: eventos.fechafinal ? eventos.fechafinal.format(DATE_TIME_FORMAT) : null,
      fechacreacion: eventos.fechacreacion ? eventos.fechacreacion.format(DATE_TIME_FORMAT) : null,
      imagenUrl: eventos.imagenUrl,
      imagenUrlContentType: eventos.imagenUrlContentType,
      videoUrl: eventos.videoUrl,
      idciudad: eventos.idciudad,
      latitud: eventos.latitud,
      longitud: eventos.longitud,
      estadoActivo: eventos.estadoActivo
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
    const eventos = this.createFromForm();
    if (eventos.id !== undefined) {
      this.subscribeToSaveResponse(this.eventosService.update(eventos));
    } else {
      this.subscribeToSaveResponse(this.eventosService.create(eventos));
    }
  }

  private createFromForm(): IEventos {
    return {
      ...new Eventos(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      decripcion: this.editForm.get(['decripcion'])!.value,
      fechainicio: this.editForm.get(['fechainicio'])!.value
        ? moment(this.editForm.get(['fechainicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechafinal: this.editForm.get(['fechafinal'])!.value ? moment(this.editForm.get(['fechafinal'])!.value, DATE_TIME_FORMAT) : undefined,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      latitud: this.editForm.get(['latitud'])!.value,
      longitud: this.editForm.get(['longitud'])!.value,
      estadoActivo: this.editForm.get(['estadoActivo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventos>>): void {
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
