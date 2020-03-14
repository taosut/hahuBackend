import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IImageMetaData, ImageMetaData } from 'app/shared/model/image-meta-data.model';
import { ImageMetaDataService } from './image-meta-data.service';
import { ImageMetaDataComponent } from './image-meta-data.component';
import { ImageMetaDataDetailComponent } from './image-meta-data-detail.component';
import { ImageMetaDataUpdateComponent } from './image-meta-data-update.component';

@Injectable({ providedIn: 'root' })
export class ImageMetaDataResolve implements Resolve<IImageMetaData> {
  constructor(private service: ImageMetaDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImageMetaData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((imageMetaData: HttpResponse<ImageMetaData>) => {
          if (imageMetaData.body) {
            return of(imageMetaData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ImageMetaData());
  }
}

export const imageMetaDataRoute: Routes = [
  {
    path: '',
    component: ImageMetaDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hahuApp.imageMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImageMetaDataDetailComponent,
    resolve: {
      imageMetaData: ImageMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.imageMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImageMetaDataUpdateComponent,
    resolve: {
      imageMetaData: ImageMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.imageMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImageMetaDataUpdateComponent,
    resolve: {
      imageMetaData: ImageMetaDataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hahuApp.imageMetaData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
