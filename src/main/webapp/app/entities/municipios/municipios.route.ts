import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMunicipios, Municipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';
import { MunicipiosComponent } from './municipios.component';
import { MunicipiosDetailComponent } from './municipios-detail.component';
import { MunicipiosUpdateComponent } from './municipios-update.component';

@Injectable({ providedIn: 'root' })
export class MunicipiosResolve implements Resolve<IMunicipios> {
  constructor(private service: MunicipiosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMunicipios> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((municipios: HttpResponse<Municipios>) => {
          if (municipios.body) {
            return of(municipios.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Municipios());
  }
}

export const municipiosRoute: Routes = [
  {
    path: '',
    component: MunicipiosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.municipios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MunicipiosDetailComponent,
    resolve: {
      municipios: MunicipiosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.municipios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MunicipiosUpdateComponent,
    resolve: {
      municipios: MunicipiosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.municipios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MunicipiosUpdateComponent,
    resolve: {
      municipios: MunicipiosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.municipios.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
