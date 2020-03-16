import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoCliente } from 'app/shared/model/estado-cliente.model';

@Component({
  selector: 'jhi-estado-cliente-detail',
  templateUrl: './estado-cliente-detail.component.html'
})
export class EstadoClienteDetailComponent implements OnInit {
  estadoCliente: IEstadoCliente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoCliente }) => (this.estadoCliente = estadoCliente));
  }

  previousState(): void {
    window.history.back();
  }
}
