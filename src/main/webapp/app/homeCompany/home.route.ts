import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import { JhiResolvePagingParams } from 'ng-jhipster';

export const HOME_ROUTE: Route = {
  path: 'homeCompany',
  component: HomeComponent,
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};
