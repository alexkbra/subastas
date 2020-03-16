import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDispositivo, Dispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';
import { DispositivoComponent } from './dispositivo.component';
import { DispositivoDetailComponent } from './dispositivo-detail.component';
import { DispositivoUpdateComponent } from './dispositivo-update.component';

@Injectable({ providedIn: 'root' })
export class DispositivoResolve implements Resolve<IDispositivo> {
  constructor(private service: DispositivoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDispositivo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dispositivo: HttpResponse<Dispositivo>) => {
          if (dispositivo.body) {
            return of(dispositivo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dispositivo());
  }
}

export const dispositivoRoute: Routes = [
  {
    path: '',
    component: DispositivoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.dispositivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DispositivoDetailComponent,
    resolve: {
      dispositivo: DispositivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.dispositivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DispositivoUpdateComponent,
    resolve: {
      dispositivo: DispositivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.dispositivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DispositivoUpdateComponent,
    resolve: {
      dispositivo: DispositivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.dispositivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
