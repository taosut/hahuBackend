import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdditionalUserInfo, AdditionalUserInfo } from 'app/shared/model/additional-user-info.model';
import { AdditionalUserInfoService } from './additional-user-info.service';
import { AdditionalUserInfoComponent } from './additional-user-info.component';
import { AdditionalUserInfoDetailComponent } from './additional-user-info-detail.component';
import { AdditionalUserInfoUpdateComponent } from './additional-user-info-update.component';

@Injectable({ providedIn: 'root' })
export class AdditionalUserInfoResolve implements Resolve<IAdditionalUserInfo> {
  constructor(private service: AdditionalUserInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdditionalUserInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((additionalUserInfo: HttpResponse<AdditionalUserInfo>) => {
          if (additionalUserInfo.body) {
            return of(additionalUserInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdditionalUserInfo());
  }
}

export const additionalUserInfoRoute: Routes = [
  {
    path: '',
    component: AdditionalUserInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.additionalUserInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdditionalUserInfoDetailComponent,
    resolve: {
      additionalUserInfo: AdditionalUserInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.additionalUserInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdditionalUserInfoUpdateComponent,
    resolve: {
      additionalUserInfo: AdditionalUserInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.additionalUserInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdditionalUserInfoUpdateComponent,
    resolve: {
      additionalUserInfo: AdditionalUserInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.additionalUserInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
