import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPujadores } from 'app/shared/model/pujadores.model';

@Component({
  selector: 'jhi-pujadores-detail',
  templateUrl: './pujadores-detail.component.html'
})
export class PujadoresDetailComponent implements OnInit {
  pujadores: IPujadores | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pujadores }) => (this.pujadores = pujadores));
  }

  previousState(): void {
    window.history.back();
  }
}
