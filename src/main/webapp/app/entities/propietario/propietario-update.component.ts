import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPropietario, Propietario } from 'app/shared/model/propietario.model';
import { PropietarioService } from './propietario.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/tipo-documento/tipo-documento.service';

@Component({
  selector: 'jhi-propietario-update',
  templateUrl: './propietario-update.component.html'
})
export class PropietarioUpdateComponent implements OnInit {
  isSaving = false;
  tipodocumentos: ITipoDocumento[] = [];

  editForm = this.fb.group({
    id: [],
    numeroDocumento: [null, [Validators.required, Validators.min(5), Validators.max(20)]],
    nombreORazonSocial: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    correo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
    telefonocelular: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    telefonofijo: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonoempresarial: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    direccionresidencial: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    direccionempresarial: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    idusuario: [],
    imagenUrl: [],
    imagenUrlContentType: [],
    idciudad: [],
    tipoDocumento: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected propietarioService: PropietarioService,
    protected tipoDocumentoService: TipoDocumentoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ propietario }) => {
      this.updateForm(propietario);

      this.tipoDocumentoService.query().subscribe((res: HttpResponse<ITipoDocumento[]>) => (this.tipodocumentos = res.body || []));
    });
  }

  updateForm(propietario: IPropietario): void {
    this.editForm.patchValue({
      id: propietario.id,
      numeroDocumento: propietario.numeroDocumento,
      nombreORazonSocial: propietario.nombreORazonSocial,
      correo: propietario.correo,
      telefonocelular: propietario.telefonocelular,
      telefonofijo: propietario.telefonofijo,
      telefonoempresarial: propietario.telefonoempresarial,
      direccionresidencial: propietario.direccionresidencial,
      direccionempresarial: propietario.direccionempresarial,
      idusuario: propietario.idusuario,
      imagenUrl: propietario.imagenUrl,
      imagenUrlContentType: propietario.imagenUrlContentType,
      idciudad: propietario.idciudad,
      tipoDocumento: propietario.tipoDocumento
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
    const propietario = this.createFromForm();
    if (propietario.id !== undefined) {
      this.subscribeToSaveResponse(this.propietarioService.update(propietario));
    } else {
      this.subscribeToSaveResponse(this.propietarioService.create(propietario));
    }
  }

  private createFromForm(): IPropietario {
    return {
      ...new Propietario(),
      id: this.editForm.get(['id'])!.value,
      numeroDocumento: this.editForm.get(['numeroDocumento'])!.value,
      nombreORazonSocial: this.editForm.get(['nombreORazonSocial'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      telefonocelular: this.editForm.get(['telefonocelular'])!.value,
      telefonofijo: this.editForm.get(['telefonofijo'])!.value,
      telefonoempresarial: this.editForm.get(['telefonoempresarial'])!.value,
      direccionresidencial: this.editForm.get(['direccionresidencial'])!.value,
      direccionempresarial: this.editForm.get(['direccionempresarial'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropietario>>): void {
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

  trackById(index: number, item: ITipoDocumento): any {
    return item.id;
  }
}
