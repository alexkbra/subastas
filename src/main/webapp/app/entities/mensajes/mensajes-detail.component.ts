import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMensajes } from 'app/shared/model/mensajes.model';

@Component({
  selector: 'jhi-mensajes-detail',
  templateUrl: './mensajes-detail.component.html'
})
export class MensajesDetailComponent implements OnInit {
  mensajes: IMensajes | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensajes }) => (this.mensajes = mensajes));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
