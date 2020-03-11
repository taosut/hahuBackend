import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsersConnection, UsersConnection } from 'app/shared/model/users-connection.model';
import { UsersConnectionService } from './users-connection.service';
import { UsersConnectionComponent } from './users-connection.component';
import { UsersConnectionDetailComponent } from './users-connection-detail.component';
import { UsersConnectionUpdateComponent } from './users-connection-update.component';

@Injectable({ providedIn: 'root' })
export class UsersConnectionResolve implements Resolve<IUsersConnection> {
  constructor(private service: UsersConnectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsersConnection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usersConnection: HttpResponse<UsersConnection>) => {
          if (usersConnection.body) {
            return of(usersConnection.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsersConnection());
  }
}

export const usersConnectionRoute: Routes = [
  {
    path: '',
    component: UsersConnectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.usersConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsersConnectionDetailComponent,
    resolve: {
      usersConnection: UsersConnectionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.usersConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsersConnectionUpdateComponent,
    resolve: {
      usersConnection: UsersConnectionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.usersConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsersConnectionUpdateComponent,
    resolve: {
      usersConnection: UsersConnectionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.usersConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
