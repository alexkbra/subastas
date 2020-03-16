import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILotesToAnimales, LotesToAnimales } from 'app/shared/model/lotes-to-animales.model';
import { LotesToAnimalesService } from './lotes-to-animales.service';
import { ILotes } from 'app/shared/model/lotes.model';
import { LotesService } from 'app/entities/lotes/lotes.service';
import { IAnimales } from 'app/shared/model/animales.model';
import { AnimalesService } from 'app/entities/animales/animales.service';

type SelectableEntity = ILotes | IAnimales;

@Component({
  selector: 'jhi-lotes-to-animales-update',
  templateUrl: './lotes-to-animales-update.component.html'
})
export class LotesToAnimalesUpdateComponent implements OnInit {
  isSaving = false;
  lotes: ILotes[] = [];
  animales: IAnimales[] = [];

  editForm = this.fb.group({
    id: [],
    cantidad: [null, [Validators.required]],
    lotes: [null, Validators.required],
    animales: [null, Validators.required]
  });

  constructor(
    protected lotesToAnimalesService: LotesToAnimalesService,
    protected lotesService: LotesService,
    protected animalesService: AnimalesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotesToAnimales }) => {
      this.updateForm(lotesToAnimales);

      this.lotesService.query().subscribe((res: HttpResponse<ILotes[]>) => (this.lotes = res.body || []));

      this.animalesService.query().subscribe((res: HttpResponse<IAnimales[]>) => (this.animales = res.body || []));
    });
  }

  updateForm(lotesToAnimales: ILotesToAnimales): void {
    this.editForm.patchValue({
      id: lotesToAnimales.id,
      cantidad: lotesToAnimales.cantidad,
      lotes: lotesToAnimales.lotes,
      animales: lotesToAnimales.animales
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lotesToAnimales = this.createFromForm();
    if (lotesToAnimales.id !== undefined) {
      this.subscribeToSaveResponse(this.lotesToAnimalesService.update(lotesToAnimales));
    } else {
      this.subscribeToSaveResponse(this.lotesToAnimalesService.create(lotesToAnimales));
    }
  }

  private createFromForm(): ILotesToAnimales {
    return {
      ...new LotesToAnimales(),
      id: this.editForm.get(['id'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      lotes: this.editForm.get(['lotes'])!.value,
      animales: this.editForm.get(['animales'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILotesToAnimales>>): void {
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
