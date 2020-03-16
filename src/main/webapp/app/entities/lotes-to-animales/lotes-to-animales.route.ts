import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILotesToAnimales, LotesToAnimales } from 'app/shared/model/lotes-to-animales.model';
import { LotesToAnimalesService } from './lotes-to-animales.service';
import { LotesToAnimalesComponent } from './lotes-to-animales.component';
import { LotesToAnimalesDetailComponent } from './lotes-to-animales-detail.component';
import { LotesToAnimalesUpdateComponent } from './lotes-to-animales-update.component';

@Injectable({ providedIn: 'root' })
export class LotesToAnimalesResolve implements Resolve<ILotesToAnimales> {
  constructor(private service: LotesToAnimalesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILotesToAnimales> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lotesToAnimales: HttpResponse<LotesToAnimales>) => {
          if (lotesToAnimales.body) {
            return of(lotesToAnimales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LotesToAnimales());
  }
}

export const lotesToAnimalesRoute: Routes = [
  {
    path: '',
    component: LotesToAnimalesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.lotesToAnimales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LotesToAnimalesDetailComponent,
    resolve: {
      lotesToAnimales: LotesToAnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.lotesToAnimales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LotesToAnimalesUpdateComponent,
    resolve: {
      lotesToAnimales: LotesToAnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.lotesToAnimales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LotesToAnimalesUpdateComponent,
    resolve: {
      lotesToAnimales: LotesToAnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.lotesToAnimales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
