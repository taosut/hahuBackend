import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILikes, Likes } from 'app/shared/model/likes.model';
import { LikesService } from './likes.service';
import { LikesComponent } from './likes.component';
import { LikesDetailComponent } from './likes-detail.component';
import { LikesUpdateComponent } from './likes-update.component';

@Injectable({ providedIn: 'root' })
export class LikesResolve implements Resolve<ILikes> {
  constructor(private service: LikesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILikes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((likes: HttpResponse<Likes>) => {
          if (likes.body) {
            return of(likes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Likes());
  }
}

export const likesRoute: Routes = [
  {
    path: '',
    component: LikesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.likes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LikesDetailComponent,
    resolve: {
      likes: LikesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.likes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LikesUpdateComponent,
    resolve: {
      likes: LikesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.likes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LikesUpdateComponent,
    resolve: {
      likes: LikesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.likes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
