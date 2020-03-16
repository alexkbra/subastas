import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMensajes, Mensajes } from 'app/shared/model/mensajes.model';
import { MensajesService } from './mensajes.service';
import { MensajesComponent } from './mensajes.component';
import { MensajesDetailComponent } from './mensajes-detail.component';
import { MensajesUpdateComponent } from './mensajes-update.component';

@Injectable({ providedIn: 'root' })
export class MensajesResolve implements Resolve<IMensajes> {
  constructor(private service: MensajesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMensajes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mensajes: HttpResponse<Mensajes>) => {
          if (mensajes.body) {
            return of(mensajes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mensajes());
  }
}

export const mensajesRoute: Routes = [
  {
    path: '',
    component: MensajesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.mensajes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MensajesDetailComponent,
    resolve: {
      mensajes: MensajesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.mensajes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MensajesUpdateComponent,
    resolve: {
      mensajes: MensajesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.mensajes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MensajesUpdateComponent,
    resolve: {
      mensajes: MensajesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.mensajes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
