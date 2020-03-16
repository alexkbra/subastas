import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoCliente, EstadoCliente } from 'app/shared/model/estado-cliente.model';
import { EstadoClienteService } from './estado-cliente.service';
import { EstadoClienteComponent } from './estado-cliente.component';
import { EstadoClienteDetailComponent } from './estado-cliente-detail.component';
import { EstadoClienteUpdateComponent } from './estado-cliente-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoClienteResolve implements Resolve<IEstadoCliente> {
  constructor(private service: EstadoClienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoCliente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoCliente: HttpResponse<EstadoCliente>) => {
          if (estadoCliente.body) {
            return of(estadoCliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoCliente());
  }
}

export const estadoClienteRoute: Routes = [
  {
    path: '',
    component: EstadoClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.estadoCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoClienteDetailComponent,
    resolve: {
      estadoCliente: EstadoClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.estadoCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoClienteUpdateComponent,
    resolve: {
      estadoCliente: EstadoClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.estadoCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoClienteUpdateComponent,
    resolve: {
      estadoCliente: EstadoClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.estadoCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
