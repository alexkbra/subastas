import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEspecies, Especies } from 'app/shared/model/especies.model';
import { EspeciesService } from './especies.service';
import { EspeciesComponent } from './especies.component';
import { EspeciesDetailComponent } from './especies-detail.component';
import { EspeciesUpdateComponent } from './especies-update.component';

@Injectable({ providedIn: 'root' })
export class EspeciesResolve implements Resolve<IEspecies> {
  constructor(private service: EspeciesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEspecies> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((especies: HttpResponse<Especies>) => {
          if (especies.body) {
            return of(especies.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Especies());
  }
}

export const especiesRoute: Routes = [
  {
    path: '',
    component: EspeciesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.especies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EspeciesDetailComponent,
    resolve: {
      especies: EspeciesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.especies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EspeciesUpdateComponent,
    resolve: {
      especies: EspeciesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.especies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EspeciesUpdateComponent,
    resolve: {
      especies: EspeciesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.especies.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
