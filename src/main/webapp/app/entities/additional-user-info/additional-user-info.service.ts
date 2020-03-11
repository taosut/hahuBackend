import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

type EntityResponseType = HttpResponse<IAdditionalUserInfo>;
type EntityArrayResponseType = HttpResponse<IAdditionalUserInfo[]>;

@Injectable({ providedIn: 'root' })
export class AdditionalUserInfoService {
  public resourceUrl = SERVER_API_URL + 'api/additional-user-infos';

  constructor(protected http: HttpClient) {}

  create(additionalUserInfo: IAdditionalUserInfo): Observable<EntityResponseType> {
    return this.http.post<IAdditionalUserInfo>(this.resourceUrl, additionalUserInfo, { observe: 'response' });
  }

  update(additionalUserInfo: IAdditionalUserInfo): Observable<EntityResponseType> {
    return this.http.put<IAdditionalUserInfo>(this.resourceUrl, additionalUserInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdditionalUserInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdditionalUserInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
