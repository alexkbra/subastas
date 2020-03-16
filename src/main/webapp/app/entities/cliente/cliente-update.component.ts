import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/tipo-documento/tipo-documento.service';
import { IEstadoCliente } from 'app/shared/model/estado-cliente.model';
import { EstadoClienteService } from 'app/entities/estado-cliente/estado-cliente.service';

type SelectableEntity = ITipoDocumento | IEstadoCliente;

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  tipodocumentos: ITipoDocumento[] = [];
  estadoclientes: IEstadoCliente[] = [];
  fechanacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    numeroDocumento: [null, [Validators.required, Validators.min(5), Validators.max(20)]],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    apellido: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    correo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
    nombrerepresentantelegal: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    telefonocelular: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonofijo: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonoempresarial: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonorepresentantelegal: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    direccionresidencial: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    direccionempresarial: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    direccionrepresentantelegal: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    fechanacimiento: [null, [Validators.required]],
    idusuario: [],
    imagenUrl: [],
    imagenUrlContentType: [],
    idciudad: [],
    anonimo: [],
    tipoDocumento: [null, Validators.required],
    estadocliente: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService,
    protected tipoDocumentoService: TipoDocumentoService,
    protected estadoClienteService: EstadoClienteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.tipoDocumentoService.query().subscribe((res: HttpResponse<ITipoDocumento[]>) => (this.tipodocumentos = res.body || []));

      this.estadoClienteService.query().subscribe((res: HttpResponse<IEstadoCliente[]>) => (this.estadoclientes = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      numeroDocumento: cliente.numeroDocumento,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      correo: cliente.correo,
      nombrerepresentantelegal: cliente.nombrerepresentantelegal,
      telefonocelular: cliente.telefonocelular,
      telefonofijo: cliente.telefonofijo,
      telefonoempresarial: cliente.telefonoempresarial,
      telefonorepresentantelegal: cliente.telefonorepresentantelegal,
      direccionresidencial: cliente.direccionresidencial,
      direccionempresarial: cliente.direccionempresarial,
      direccionrepresentantelegal: cliente.direccionrepresentantelegal,
      fechanacimiento: cliente.fechanacimiento,
      idusuario: cliente.idusuario,
      imagenUrl: cliente.imagenUrl,
      imagenUrlContentType: cliente.imagenUrlContentType,
      idciudad: cliente.idciudad,
      anonimo: cliente.anonimo,
      tipoDocumento: cliente.tipoDocumento,
      estadocliente: cliente.estadocliente
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
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      numeroDocumento: this.editForm.get(['numeroDocumento'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      nombrerepresentantelegal: this.editForm.get(['nombrerepresentantelegal'])!.value,
      telefonocelular: this.editForm.get(['telefonocelular'])!.value,
      telefonofijo: this.editForm.get(['telefonofijo'])!.value,
      telefonoempresarial: this.editForm.get(['telefonoempresarial'])!.value,
      telefonorepresentantelegal: this.editForm.get(['telefonorepresentantelegal'])!.value,
      direccionresidencial: this.editForm.get(['direccionresidencial'])!.value,
      direccionempresarial: this.editForm.get(['direccionempresarial'])!.value,
      direccionrepresentantelegal: this.editForm.get(['direccionrepresentantelegal'])!.value,
      fechanacimiento: this.editForm.get(['fechanacimiento'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      anonimo: this.editForm.get(['anonimo'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value,
      estadocliente: this.editForm.get(['estadocliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
