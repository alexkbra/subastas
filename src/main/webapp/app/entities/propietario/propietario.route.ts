import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPropietario, Propietario } from 'app/shared/model/propietario.model';
import { PropietarioService } from './propietario.service';
import { PropietarioComponent } from './propietario.component';
import { PropietarioDetailComponent } from './propietario-detail.component';
import { PropietarioUpdateComponent } from './propietario-update.component';

@Injectable({ providedIn: 'root' })
export class PropietarioResolve implements Resolve<IPropietario> {
  constructor(private service: PropietarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPropietario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((propietario: HttpResponse<Propietario>) => {
          if (propietario.body) {
            return of(propietario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Propietario());
  }
}

export const propietarioRoute: Routes = [
  {
    path: '',
    component: PropietarioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.propietario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PropietarioDetailComponent,
    resolve: {
      propietario: PropietarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.propietario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PropietarioUpdateComponent,
    resolve: {
      propietario: PropietarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.propietario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PropietarioUpdateComponent,
    resolve: {
      propietario: PropietarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.propietario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
