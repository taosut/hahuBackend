import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShares, Shares } from 'app/shared/model/shares.model';
import { SharesService } from './shares.service';
import { SharesComponent } from './shares.component';
import { SharesDetailComponent } from './shares-detail.component';
import { SharesUpdateComponent } from './shares-update.component';

@Injectable({ providedIn: 'root' })
export class SharesResolve implements Resolve<IShares> {
  constructor(private service: SharesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShares> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shares: HttpResponse<Shares>) => {
          if (shares.body) {
            return of(shares.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Shares());
  }
}

export const sharesRoute: Routes = [
  {
    path: '',
    component: SharesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.shares.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SharesDetailComponent,
    resolve: {
      shares: SharesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.shares.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SharesUpdateComponent,
    resolve: {
      shares: SharesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.shares.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SharesUpdateComponent,
    resolve: {
      shares: SharesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.shares.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
