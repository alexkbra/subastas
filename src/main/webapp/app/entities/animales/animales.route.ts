import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnimales, Animales } from 'app/shared/model/animales.model';
import { AnimalesService } from './animales.service';
import { AnimalesComponent } from './animales.component';
import { AnimalesDetailComponent } from './animales-detail.component';
import { AnimalesUpdateComponent } from './animales-update.component';

@Injectable({ providedIn: 'root' })
export class AnimalesResolve implements Resolve<IAnimales> {
  constructor(private service: AnimalesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimales> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((animales: HttpResponse<Animales>) => {
          if (animales.body) {
            return of(animales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Animales());
  }
}

export const animalesRoute: Routes = [
  {
    path: '',
    component: AnimalesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'subastasApp.animales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalesDetailComponent,
    resolve: {
      animales: AnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.animales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalesUpdateComponent,
    resolve: {
      animales: AnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.animales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalesUpdateComponent,
    resolve: {
      animales: AnimalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'subastasApp.animales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
