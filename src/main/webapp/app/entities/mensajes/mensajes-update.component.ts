import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMensajes, Mensajes } from 'app/shared/model/mensajes.model';
import { MensajesService } from './mensajes.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-mensajes-update',
  templateUrl: './mensajes-update.component.html'
})
export class MensajesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    cuerpo: [null, [Validators.required]],
    numeroLote: [],
    tipoMensaje: [],
    tipoCliente: [],
    direccion: [null, [Validators.required]],
    idusuario: [],
    idcliente: [],
    activo: [],
    valorPagar: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected mensajesService: MensajesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensajes }) => {
      this.updateForm(mensajes);
    });
  }

  updateForm(mensajes: IMensajes): void {
    this.editForm.patchValue({
      id: mensajes.id,
      titulo: mensajes.titulo,
      cuerpo: mensajes.cuerpo,
      numeroLote: mensajes.numeroLote,
      tipoMensaje: mensajes.tipoMensaje,
      tipoCliente: mensajes.tipoCliente,
      direccion: mensajes.direccion,
      idusuario: mensajes.idusuario,
      idcliente: mensajes.idcliente,
      activo: mensajes.activo,
      valorPagar: mensajes.valorPagar
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
    const mensajes = this.createFromForm();
    if (mensajes.id !== undefined) {
      this.subscribeToSaveResponse(this.mensajesService.update(mensajes));
    } else {
      this.subscribeToSaveResponse(this.mensajesService.create(mensajes));
    }
  }

  private createFromForm(): IMensajes {
    return {
      ...new Mensajes(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      cuerpo: this.editForm.get(['cuerpo'])!.value,
      numeroLote: this.editForm.get(['numeroLote'])!.value,
      tipoMensaje: this.editForm.get(['tipoMensaje'])!.value,
      tipoCliente: this.editForm.get(['tipoCliente'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      idcliente: this.editForm.get(['idcliente'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      valorPagar: this.editForm.get(['valorPagar'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensajes>>): void {
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
