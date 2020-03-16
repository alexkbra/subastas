import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRazas, Razas } from 'app/shared/model/razas.model';
import { RazasService } from './razas.service';
import { RazasComponent } from './razas.component';
import { RazasDetailComponent } from './razas-detail.component';
import { RazasUpdateComponent } from './razas-update.component';

@Injectable({ providedIn: 'root' })
export class RazasResolve implements Resolve<IRazas> {
  constructor(private service: RazasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRazas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((razas: HttpResponse<Razas>) => {
          if (razas.body) {
            return of(razas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Razas());
  }
}

export const razasRoute: Routes = [
  {
    path: '',
    component: RazasComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.razas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RazasDetailComponent,
    resolve: {
      razas: RazasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.razas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RazasUpdateComponent,
    resolve: {
      razas: RazasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.razas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RazasUpdateComponent,
    resolve: {
      razas: RazasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.razas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
