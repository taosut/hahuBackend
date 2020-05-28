import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShares } from 'app/shared/model/shares.model';

type EntityResponseType = HttpResponse<IShares>;
type EntityArrayResponseType = HttpResponse<IShares[]>;

@Injectable({ providedIn: 'root' })
export class SharesService {
  public resourceUrl = SERVER_API_URL + 'api/shares';

  constructor(protected http: HttpClient) {}

  create(shares: IShares): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shares);
    return this.http
      .post<IShares>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shares: IShares): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shares);
    return this.http
      .put<IShares>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShares>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShares[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(shares: IShares): IShares {
    const copy: IShares = Object.assign({}, shares, {
      registeredTime: shares.registeredTime && shares.registeredTime.isValid() ? shares.registeredTime.toJSON() : undefined,
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
      res.body.forEach((shares: IShares) => {
        shares.registeredTime = shares.registeredTime ? moment(shares.registeredTime) : undefined;
      });
    }
    return res;
  }
}
