import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserGroup, UserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from './user-group.service';
import { UserGroupDetailComponent } from './user-group-detail.component';

@Injectable({ providedIn: 'root' })
export class UserGroupResolve implements Resolve<IUserGroup> {
  constructor(private service: UserGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userGroup: HttpResponse<UserGroup>) => {
          if (userGroup.body) {
            return of(userGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserGroup());
  }
}

export const userGroupRoute: Routes = [
  {
    path: ':id/view',
    component: UserGroupDetailComponent,
    resolve: {
      userGroup: UserGroupResolve,
    },
    data: {
      authorities: [Authority.SCHOOL_ADMIN],
      pageTitle: 'hahuApp.userGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
