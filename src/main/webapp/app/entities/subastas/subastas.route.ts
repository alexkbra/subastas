import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubastas, Subastas } from 'app/shared/model/subastas.model';
import { SubastasService } from './subastas.service';
import { SubastasComponent } from './subastas.component';
import { SubastasDetailComponent } from './subastas-detail.component';
import { SubastasUpdateComponent } from './subastas-update.component';

@Injectable({ providedIn: 'root' })
export class SubastasResolve implements Resolve<ISubastas> {
  constructor(private service: SubastasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubastas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subastas: HttpResponse<Subastas>) => {
          if (subastas.body) {
            return of(subastas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Subastas());
  }
}

export const subastasRoute: Routes = [
  {
    path: '',
    component: SubastasComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.subastas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubastasDetailComponent,
    resolve: {
      subastas: SubastasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.subastas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubastasUpdateComponent,
    resolve: {
      subastas: SubastasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.subastas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubastasUpdateComponent,
    resolve: {
      subastas: SubastasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.subastas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
