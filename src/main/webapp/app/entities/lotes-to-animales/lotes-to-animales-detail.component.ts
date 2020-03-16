import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

@Component({
  selector: 'jhi-lotes-to-animales-detail',
  templateUrl: './lotes-to-animales-detail.component.html'
})
export class LotesToAnimalesDetailComponent implements OnInit {
  lotesToAnimales: ILotesToAnimales | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotesToAnimales }) => (this.lotesToAnimales = lotesToAnimales));
  }

  previousState(): void {
    window.history.back();
  }
}
