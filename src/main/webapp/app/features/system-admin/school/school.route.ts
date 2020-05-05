import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchool, School } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';

@Injectable({ providedIn: 'root' })
export class SchoolResolve implements Resolve<ISchool> {
  constructor(private service: SchoolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchool> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((school: HttpResponse<School>) => {
          if (school.body) {
            return of(school.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new School());
  }
}

export const schoolRoute: Routes = [
  {
    path: '',
    component: SchoolComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SchoolDetailComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
      school: SchoolResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SchoolUpdateComponent,
    resolve: {
      school: SchoolResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'hahuApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
