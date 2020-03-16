import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPujas, Pujas } from 'app/shared/model/pujas.model';
import { PujasService } from './pujas.service';
import { PujasComponent } from './pujas.component';
import { PujasDetailComponent } from './pujas-detail.component';
import { PujasUpdateComponent } from './pujas-update.component';

@Injectable({ providedIn: 'root' })
export class PujasResolve implements Resolve<IPujas> {
  constructor(private service: PujasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPujas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pujas: HttpResponse<Pujas>) => {
          if (pujas.body) {
            return of(pujas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pujas());
  }
}

export const pujasRoute: Routes = [
  {
    path: '',
    component: PujasComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.pujas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PujasDetailComponent,
    resolve: {
      pujas: PujasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PujasUpdateComponent,
    resolve: {
      pujas: PujasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PujasUpdateComponent,
    resolve: {
      pujas: PujasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
