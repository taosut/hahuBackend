import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPostMetaData, PostMetaData } from 'app/shared/model/post-meta-data.model';
import { PostMetaDataService } from './post-meta-data.service';
import { PostMetaDataComponent } from './post-meta-data.component';
import { PostMetaDataDetailComponent } from './post-meta-data-detail.component';
import { PostMetaDataUpdateComponent } from './post-meta-data-update.component';

@Injectable({ providedIn: 'root' })
export class PostMetaDataResolve implements Resolve<IPostMetaData> {
  constructor(private service: PostMetaDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostMetaData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((postMetaData: HttpResponse<PostMetaData>) => {
          if (postMetaData.body) {
            return of(postMetaData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostMetaData());
  }
}

export const postMetaDataRoute: Routes = [
  {
    path: '',
    component: PostMetaDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.postMetaData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostMetaDataDetailComponent,
    resolve: {
      postMetaData: PostMetaDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.postMetaData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostMetaDataUpdateComponent,
    resolve: {
      postMetaData: PostMetaDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.postMetaData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostMetaDataUpdateComponent,
    resolve: {
      postMetaData: PostMetaDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.postMetaData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
