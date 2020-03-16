import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LotesToAnimalesService } from './lotes-to-animales.service';
import { LotesToAnimalesDeleteDialogComponent } from './lotes-to-animales-delete-dialog.component';

@Component({
  selector: 'jhi-lotes-to-animales',
  templateUrl: './lotes-to-animales.component.html'
})
export class LotesToAnimalesComponent implements OnInit, OnDestroy {
  lotesToAnimales?: ILotesToAnimales[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected lotesToAnimalesService: LotesToAnimalesService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.lotesToAnimalesService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ILotesToAnimales[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInLotesToAnimales();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILotesToAnimales): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLotesToAnimales(): void {
    this.eventSubscriber = this.eventManager.subscribe('lotesToAnimalesListModification', () => this.loadPage());
  }

  delete(lotesToAnimales: ILotesToAnimales): void {
    const modalRef = this.modalService.open(LotesToAnimalesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lotesToAnimales = lotesToAnimales;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILotesToAnimales[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/lotes-to-animales'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.lotesToAnimales = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
