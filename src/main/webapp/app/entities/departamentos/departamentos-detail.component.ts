import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartamentos } from 'app/shared/model/departamentos.model';

@Component({
  selector: 'jhi-departamentos-detail',
  templateUrl: './departamentos-detail.component.html'
})
export class DepartamentosDetailComponent implements OnInit {
  departamentos: IDepartamentos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentos }) => (this.departamentos = departamentos));
  }

  previousState(): void {
    window.history.back();
  }
}
