import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

@Component({
  selector: 'jhi-clasificacion-lote-detail',
  templateUrl: './clasificacion-lote-detail.component.html'
})
export class ClasificacionLoteDetailComponent implements OnInit {
  clasificacionLote: IClasificacionLote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clasificacionLote }) => (this.clasificacionLote = clasificacionLote));
  }

  previousState(): void {
    window.history.back();
  }
}
