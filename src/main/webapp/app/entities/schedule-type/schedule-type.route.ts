import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScheduleType, ScheduleType } from 'app/shared/model/schedule-type.model';
import { ScheduleTypeService } from './schedule-type.service';
import { ScheduleTypeComponent } from './schedule-type.component';
import { ScheduleTypeDetailComponent } from './schedule-type-detail.component';
import { ScheduleTypeUpdateComponent } from './schedule-type-update.component';

@Injectable({ providedIn: 'root' })
export class ScheduleTypeResolve implements Resolve<IScheduleType> {
  constructor(private service: ScheduleTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheduleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scheduleType: HttpResponse<ScheduleType>) => {
          if (scheduleType.body) {
            return of(scheduleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScheduleType());
  }
}

export const scheduleTypeRoute: Routes = [
  {
    path: '',
    component: ScheduleTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.scheduleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScheduleTypeDetailComponent,
    resolve: {
      scheduleType: ScheduleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.scheduleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScheduleTypeUpdateComponent,
    resolve: {
      scheduleType: ScheduleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.scheduleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScheduleTypeUpdateComponent,
    resolve: {
      scheduleType: ScheduleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.scheduleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
