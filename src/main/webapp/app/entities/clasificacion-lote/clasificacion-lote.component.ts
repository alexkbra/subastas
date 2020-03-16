import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ClasificacionLoteService } from './clasificacion-lote.service';
import { ClasificacionLoteDeleteDialogComponent } from './clasificacion-lote-delete-dialog.component';

@Component({
  selector: 'jhi-clasificacion-lote',
  templateUrl: './clasificacion-lote.component.html'
})
export class ClasificacionLoteComponent implements OnInit, OnDestroy {
  clasificacionLotes?: IClasificacionLote[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected clasificacionLoteService: ClasificacionLoteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.clasificacionLoteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IClasificacionLote[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInClasificacionLotes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClasificacionLote): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClasificacionLotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('clasificacionLoteListModification', () => this.loadPage());
  }

  delete(clasificacionLote: IClasificacionLote): void {
    const modalRef = this.modalService.open(ClasificacionLoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.clasificacionLote = clasificacionLote;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IClasificacionLote[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/clasificacion-lote'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.clasificacionLotes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
