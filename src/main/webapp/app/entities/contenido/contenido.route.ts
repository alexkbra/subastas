import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';
import { ContenidoComponent } from './contenido.component';
import { ContenidoDetailComponent } from './contenido-detail.component';
import { ContenidoUpdateComponent } from './contenido-update.component';

@Injectable({ providedIn: 'root' })
export class ContenidoResolve implements Resolve<IContenido> {
  constructor(private service: ContenidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContenido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contenido: HttpResponse<Contenido>) => {
          if (contenido.body) {
            return of(contenido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contenido());
  }
}

export const contenidoRoute: Routes = [
  {
    path: '',
    component: ContenidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.contenido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContenidoDetailComponent,
    resolve: {
      contenido: ContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.contenido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContenidoUpdateComponent,
    resolve: {
      contenido: ContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.contenido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContenidoUpdateComponent,
    resolve: {
      contenido: ContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.contenido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
