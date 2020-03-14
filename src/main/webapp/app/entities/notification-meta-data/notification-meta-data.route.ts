import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotificationMetaData, NotificationMetaData } from 'app/shared/model/notification-meta-data.model';
import { NotificationMetaDataService } from './notification-meta-data.service';
import { NotificationMetaDataComponent } from './notification-meta-data.component';
import { NotificationMetaDataDetailComponent } from './notification-meta-data-detail.component';
import { NotificationMetaDataUpdateComponent } from './notification-meta-data-update.component';

@Injectable({ providedIn: 'root' })
export class NotificationMetaDataResolve implements Resolve<INotificationMetaData> {
  constructor(private service: NotificationMetaDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotificationMetaData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notificationMetaData: HttpResponse<NotificationMetaData>) => {
          if (notificationMetaData.body) {
            return of(notificationMetaData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotificationMetaData());
  }
}

export const notificationMetaDataRoute: Routes = [
  {
    path: '',
    component: NotificationMetaDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.notificationMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificationMetaDataDetailComponent,
    resolve: {
      notificationMetaData: NotificationMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.notificationMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificationMetaDataUpdateComponent,
    resolve: {
      notificationMetaData: NotificationMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.notificationMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificationMetaDataUpdateComponent,
    resolve: {
      notificationMetaData: NotificationMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.notificationMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
