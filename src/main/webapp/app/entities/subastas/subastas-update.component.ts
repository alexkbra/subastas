import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISubastas, Subastas } from 'app/shared/model/subastas.model';
import { SubastasService } from './subastas.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEventos } from 'app/shared/model/eventos.model';
import { EventosService } from 'app/entities/eventos/eventos.service';

@Component({
  selector: 'jhi-subastas-update',
  templateUrl: './subastas-update.component.html'
})
export class SubastasUpdateComponent implements OnInit {
  isSaving = false;
  eventos: IEventos[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    decripcion: [],
    fechainicio: [null, [Validators.required]],
    fechafinal: [null, [Validators.required]],
    timpoRecloGanador: [null, [Validators.required]],
    fechacreacion: [null, [Validators.required]],
    valorinicial: [null, [Validators.required]],
    valoractual: [null, [Validators.required]],
    valortope: [null, [Validators.required]],
    pagaAnticipo: [null, [Validators.required]],
    pesobaseporkg: [],
    pesototallote: [],
    valorAnticipo: [],
    imagenUrl: [],
    imagenUrlContentType: [],
    videoUrl: [null, [Validators.maxLength(500)]],
    estadoActivo: [null, [Validators.required]],
    eventos: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected subastasService: SubastasService,
    protected eventosService: EventosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subastas }) => {
      if (!subastas.id) {
        const today = moment().startOf('day');
        subastas.fechainicio = today;
        subastas.fechafinal = today;
        subastas.fechacreacion = today;
      }

      this.updateForm(subastas);

      this.eventosService.query().subscribe((res: HttpResponse<IEventos[]>) => (this.eventos = res.body || []));
    });
  }

  updateForm(subastas: ISubastas): void {
    this.editForm.patchValue({
      id: subastas.id,
      nombre: subastas.nombre,
      decripcion: subastas.decripcion,
      fechainicio: subastas.fechainicio ? subastas.fechainicio.format(DATE_TIME_FORMAT) : null,
      fechafinal: subastas.fechafinal ? subastas.fechafinal.format(DATE_TIME_FORMAT) : null,
      timpoRecloGanador: subastas.timpoRecloGanador,
      fechacreacion: subastas.fechacreacion ? subastas.fechacreacion.format(DATE_TIME_FORMAT) : null,
      valorinicial: subastas.valorinicial,
      valoractual: subastas.valoractual,
      valortope: subastas.valortope,
      pagaAnticipo: subastas.pagaAnticipo,
      pesobaseporkg: subastas.pesobaseporkg,
      pesototallote: subastas.pesototallote,
      valorAnticipo: subastas.valorAnticipo,
      imagenUrl: subastas.imagenUrl,
      imagenUrlContentType: subastas.imagenUrlContentType,
      videoUrl: subastas.videoUrl,
      estadoActivo: subastas.estadoActivo,
      eventos: subastas.eventos
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
    const subastas = this.createFromForm();
    if (subastas.id !== undefined) {
      this.subscribeToSaveResponse(this.subastasService.update(subastas));
    } else {
      this.subscribeToSaveResponse(this.subastasService.create(subastas));
    }
  }

  private createFromForm(): ISubastas {
    return {
      ...new Subastas(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      decripcion: this.editForm.get(['decripcion'])!.value,
      fechainicio: this.editForm.get(['fechainicio'])!.value
        ? moment(this.editForm.get(['fechainicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechafinal: this.editForm.get(['fechafinal'])!.value ? moment(this.editForm.get(['fechafinal'])!.value, DATE_TIME_FORMAT) : undefined,
      timpoRecloGanador: this.editForm.get(['timpoRecloGanador'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      valorinicial: this.editForm.get(['valorinicial'])!.value,
      valoractual: this.editForm.get(['valoractual'])!.value,
      valortope: this.editForm.get(['valortope'])!.value,
      pagaAnticipo: this.editForm.get(['pagaAnticipo'])!.value,
      pesobaseporkg: this.editForm.get(['pesobaseporkg'])!.value,
      pesototallote: this.editForm.get(['pesototallote'])!.value,
      valorAnticipo: this.editForm.get(['valorAnticipo'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      estadoActivo: this.editForm.get(['estadoActivo'])!.value,
      eventos: this.editForm.get(['eventos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubastas>>): void {
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

  trackById(index: number, item: IEventos): any {
    return item.id;
  }
}
