import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILikes } from 'app/shared/model/likes.model';

type EntityResponseType = HttpResponse<ILikes>;
type EntityArrayResponseType = HttpResponse<ILikes[]>;

@Injectable({ providedIn: 'root' })
export class LikesService {
  public resourceUrl = SERVER_API_URL + 'api/likes';

  constructor(protected http: HttpClient) {}

  create(likes: ILikes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(likes);
    return this.http
      .post<ILikes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(likes: ILikes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(likes);
    return this.http
      .put<ILikes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILikes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILikes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(likes: ILikes): ILikes {
    const copy: ILikes = Object.assign({}, likes, {
      registeredTime: likes.registeredTime && likes.registeredTime.isValid() ? likes.registeredTime.toJSON() : undefined
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
      res.body.forEach((likes: ILikes) => {
        likes.registeredTime = likes.registeredTime ? moment(likes.registeredTime) : undefined;
      });
    }
    return res;
  }
}
