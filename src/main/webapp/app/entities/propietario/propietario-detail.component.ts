import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPropietario } from 'app/shared/model/propietario.model';

@Component({
  selector: 'jhi-propietario-detail',
  templateUrl: './propietario-detail.component.html'
})
export class PropietarioDetailComponent implements OnInit {
  propietario: IPropietario | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ propietario }) => (this.propietario = propietario));
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
