import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IViews } from 'app/shared/model/views.model';

type EntityResponseType = HttpResponse<IViews>;
type EntityArrayResponseType = HttpResponse<IViews[]>;

@Injectable({ providedIn: 'root' })
export class ViewsService {
  public resourceUrl = SERVER_API_URL + 'api/views';

  constructor(protected http: HttpClient) {}

  create(views: IViews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(views);
    return this.http
      .post<IViews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(views: IViews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(views);
    return this.http
      .put<IViews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IViews>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IViews[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(views: IViews): IViews {
    const copy: IViews = Object.assign({}, views, {
      registeredTime: views.registeredTime && views.registeredTime.isValid() ? views.registeredTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registeredTime = res.body.registeredTime ? moment(res.body.registeredTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((views: IViews) => {
        views.registeredTime = views.registeredTime ? moment(views.registeredTime) : undefined;
      });
    }
    return res;
  }
}
