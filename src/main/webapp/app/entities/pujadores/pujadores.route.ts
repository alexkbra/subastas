import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPujadores, Pujadores } from 'app/shared/model/pujadores.model';
import { PujadoresService } from './pujadores.service';
import { PujadoresComponent } from './pujadores.component';
import { PujadoresDetailComponent } from './pujadores-detail.component';
import { PujadoresUpdateComponent } from './pujadores-update.component';

@Injectable({ providedIn: 'root' })
export class PujadoresResolve implements Resolve<IPujadores> {
  constructor(private service: PujadoresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPujadores> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pujadores: HttpResponse<Pujadores>) => {
          if (pujadores.body) {
            return of(pujadores.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pujadores());
  }
}

export const pujadoresRoute: Routes = [
  {
    path: '',
    component: PujadoresComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.pujadores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PujadoresDetailComponent,
    resolve: {
      pujadores: PujadoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujadores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PujadoresUpdateComponent,
    resolve: {
      pujadores: PujadoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujadores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PujadoresUpdateComponent,
    resolve: {
      pujadores: PujadoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.pujadores.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
