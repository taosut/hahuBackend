import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IViews, Views } from 'app/shared/model/views.model';
import { ViewsService } from './views.service';
import { ViewsComponent } from './views.component';
import { ViewsDetailComponent } from './views-detail.component';
import { ViewsUpdateComponent } from './views-update.component';

@Injectable({ providedIn: 'root' })
export class ViewsResolve implements Resolve<IViews> {
  constructor(private service: ViewsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViews> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((views: HttpResponse<Views>) => {
          if (views.body) {
            return of(views.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Views());
  }
}

export const viewsRoute: Routes = [
  {
    path: '',
    component: ViewsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.views.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViewsDetailComponent,
    resolve: {
      views: ViewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.views.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ViewsUpdateComponent,
    resolve: {
      views: ViewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.views.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ViewsUpdateComponent,
    resolve: {
      views: ViewsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.views.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
