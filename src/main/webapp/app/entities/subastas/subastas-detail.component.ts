import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISubastas } from 'app/shared/model/subastas.model';

@Component({
  selector: 'jhi-subastas-detail',
  templateUrl: './subastas-detail.component.html'
})
export class SubastasDetailComponent implements OnInit {
  subastas: ISubastas | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subastas }) => (this.subastas = subastas));
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
