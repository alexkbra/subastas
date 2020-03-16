import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClasificacionLote, ClasificacionLote } from 'app/shared/model/clasificacion-lote.model';
import { ClasificacionLoteService } from './clasificacion-lote.service';
import { ClasificacionLoteComponent } from './clasificacion-lote.component';
import { ClasificacionLoteDetailComponent } from './clasificacion-lote-detail.component';
import { ClasificacionLoteUpdateComponent } from './clasificacion-lote-update.component';

@Injectable({ providedIn: 'root' })
export class ClasificacionLoteResolve implements Resolve<IClasificacionLote> {
  constructor(private service: ClasificacionLoteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClasificacionLote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((clasificacionLote: HttpResponse<ClasificacionLote>) => {
          if (clasificacionLote.body) {
            return of(clasificacionLote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClasificacionLote());
  }
}

export const clasificacionLoteRoute: Routes = [
  {
    path: '',
    component: ClasificacionLoteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.clasificacionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClasificacionLoteDetailComponent,
    resolve: {
      clasificacionLote: ClasificacionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.clasificacionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClasificacionLoteUpdateComponent,
    resolve: {
      clasificacionLote: ClasificacionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.clasificacionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClasificacionLoteUpdateComponent,
    resolve: {
      clasificacionLote: ClasificacionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.clasificacionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
