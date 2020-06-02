import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFamily, Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { FamilyComponent } from './family.component';
import { FamilyDetailComponent } from './family-detail.component';
import { FamilyUpdateComponent } from './family-update.component';

@Injectable({ providedIn: 'root' })
export class FamilyResolve implements Resolve<IFamily> {
  constructor(private service: FamilyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamily> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((family: HttpResponse<Family>) => {
          if (family.body) {
            return of(family.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Family());
  }
}

export const familyRoute: Routes = [
  {
    path: '',
    component: FamilyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.family.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FamilyDetailComponent,
    resolve: {
      family: FamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.family.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.family.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.family.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
