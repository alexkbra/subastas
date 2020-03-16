import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDepartamentos, Departamentos } from 'app/shared/model/departamentos.model';
import { DepartamentosService } from './departamentos.service';
import { DepartamentosComponent } from './departamentos.component';
import { DepartamentosDetailComponent } from './departamentos-detail.component';
import { DepartamentosUpdateComponent } from './departamentos-update.component';

@Injectable({ providedIn: 'root' })
export class DepartamentosResolve implements Resolve<IDepartamentos> {
  constructor(private service: DepartamentosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepartamentos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((departamentos: HttpResponse<Departamentos>) => {
          if (departamentos.body) {
            return of(departamentos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Departamentos());
  }
}

export const departamentosRoute: Routes = [
  {
    path: '',
    component: DepartamentosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.departamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartamentosDetailComponent,
    resolve: {
      departamentos: DepartamentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.departamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartamentosUpdateComponent,
    resolve: {
      departamentos: DepartamentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.departamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartamentosUpdateComponent,
    resolve: {
      departamentos: DepartamentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.departamentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
