import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchoolProgress, SchoolProgress } from 'app/shared/model/school-progress.model';
import { SchoolProgressService } from './school-progress.service';
import { SchoolProgressComponent } from './school-progress.component';
import { SchoolProgressDetailComponent } from './school-progress-detail.component';
import { SchoolProgressUpdateComponent } from './school-progress-update.component';

@Injectable({ providedIn: 'root' })
export class SchoolProgressResolve implements Resolve<ISchoolProgress> {
  constructor(private service: SchoolProgressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchoolProgress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schoolProgress: HttpResponse<SchoolProgress>) => {
          if (schoolProgress.body) {
            return of(schoolProgress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchoolProgress());
  }
}

export const schoolProgressRoute: Routes = [
  {
    path: '',
    component: SchoolProgressComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.schoolProgress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchoolProgressDetailComponent,
    resolve: {
      schoolProgress: SchoolProgressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.schoolProgress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchoolProgressUpdateComponent,
    resolve: {
      schoolProgress: SchoolProgressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.schoolProgress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchoolProgressUpdateComponent,
    resolve: {
      schoolProgress: SchoolProgressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.schoolProgress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
