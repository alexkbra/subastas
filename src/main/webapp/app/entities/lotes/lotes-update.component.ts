import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILotes, Lotes } from 'app/shared/model/lotes.model';
import { LotesService } from './lotes.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPropietario } from 'app/shared/model/propietario.model';
import { PropietarioService } from 'app/entities/propietario/propietario.service';
import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';
import { ClasificacionLoteService } from 'app/entities/clasificacion-lote/clasificacion-lote.service';
import { ISubastas } from 'app/shared/model/subastas.model';
import { SubastasService } from 'app/entities/subastas/subastas.service';

type SelectableEntity = IPropietario | IClasificacionLote | ISubastas;

@Component({
  selector: 'jhi-lotes-update',
  templateUrl: './lotes-update.component.html'
})
export class LotesUpdateComponent implements OnInit {
  isSaving = false;
  propietarios: IPropietario[] = [];
  clasificacionlotes: IClasificacionLote[] = [];
  subastas: ISubastas[] = [];

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    decripcion: [],
    raza: [],
    cantidadAnimales: [null, [Validators.required]],
    pesopromedio: [],
    pesototallote: [],
    pesobaseporkg: [],
    imagenUrl: [],
    imagenUrlContentType: [],
    videoUrl: [null, [Validators.maxLength(500)]],
    idciudad: [],
    propietario: [null, Validators.required],
    clasificacionlote: [null, Validators.required],
    subastas: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lotesService: LotesService,
    protected propietarioService: PropietarioService,
    protected clasificacionLoteService: ClasificacionLoteService,
    protected subastasService: SubastasService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotes }) => {
      this.updateForm(lotes);

      this.propietarioService.query().subscribe((res: HttpResponse<IPropietario[]>) => (this.propietarios = res.body || []));

      this.clasificacionLoteService
        .query()
        .subscribe((res: HttpResponse<IClasificacionLote[]>) => (this.clasificacionlotes = res.body || []));

      this.subastasService.query().subscribe((res: HttpResponse<ISubastas[]>) => (this.subastas = res.body || []));
    });
  }

  updateForm(lotes: ILotes): void {
    this.editForm.patchValue({
      id: lotes.id,
      numero: lotes.numero,
      nombre: lotes.nombre,
      decripcion: lotes.decripcion,
      raza: lotes.raza,
      cantidadAnimales: lotes.cantidadAnimales,
      pesopromedio: lotes.pesopromedio,
      pesototallote: lotes.pesototallote,
      pesobaseporkg: lotes.pesobaseporkg,
      imagenUrl: lotes.imagenUrl,
      imagenUrlContentType: lotes.imagenUrlContentType,
      videoUrl: lotes.videoUrl,
      idciudad: lotes.idciudad,
      propietario: lotes.propietario,
      clasificacionlote: lotes.clasificacionlote,
      subastas: lotes.subastas
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
    const lotes = this.createFromForm();
    if (lotes.id !== undefined) {
      this.subscribeToSaveResponse(this.lotesService.update(lotes));
    } else {
      this.subscribeToSaveResponse(this.lotesService.create(lotes));
    }
  }

  private createFromForm(): ILotes {
    return {
      ...new Lotes(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      decripcion: this.editForm.get(['decripcion'])!.value,
      raza: this.editForm.get(['raza'])!.value,
      cantidadAnimales: this.editForm.get(['cantidadAnimales'])!.value,
      pesopromedio: this.editForm.get(['pesopromedio'])!.value,
      pesototallote: this.editForm.get(['pesototallote'])!.value,
      pesobaseporkg: this.editForm.get(['pesobaseporkg'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      propietario: this.editForm.get(['propietario'])!.value,
      clasificacionlote: this.editForm.get(['clasificacionlote'])!.value,
      subastas: this.editForm.get(['subastas'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILotes>>): void {
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
