import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPujas } from 'app/shared/model/pujas.model';

@Component({
  selector: 'jhi-pujas-detail',
  templateUrl: './pujas-detail.component.html'
})
export class PujasDetailComponent implements OnInit {
  pujas: IPujas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pujas }) => (this.pujas = pujas));
  }

  previousState(): void {
    window.history.back();
  }
}
