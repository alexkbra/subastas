import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventos, Eventos } from 'app/shared/model/eventos.model';
import { EventosService } from './eventos.service';
import { EventosComponent } from './eventos.component';
import { EventosDetailComponent } from './eventos-detail.component';
import { EventosUpdateComponent } from './eventos-update.component';

@Injectable({ providedIn: 'root' })
export class EventosResolve implements Resolve<IEventos> {
  constructor(private service: EventosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventos: HttpResponse<Eventos>) => {
          if (eventos.body) {
            return of(eventos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Eventos());
  }
}

export const eventosRoute: Routes = [
  {
    path: '',
    component: EventosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.eventos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventosDetailComponent,
    resolve: {
      eventos: EventosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.eventos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventosUpdateComponent,
    resolve: {
      eventos: EventosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.eventos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventosUpdateComponent,
    resolve: {
      eventos: EventosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.eventos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
