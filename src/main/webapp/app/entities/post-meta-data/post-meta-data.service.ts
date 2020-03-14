import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPostMetaData } from 'app/shared/model/post-meta-data.model';

type EntityResponseType = HttpResponse<IPostMetaData>;
type EntityArrayResponseType = HttpResponse<IPostMetaData[]>;

@Injectable({ providedIn: 'root' })
export class PostMetaDataService {
  public resourceUrl = SERVER_API_URL + 'api/post-meta-data';

  constructor(protected http: HttpClient) {}

  create(postMetaData: IPostMetaData): Observable<EntityResponseType> {
    return this.http.post<IPostMetaData>(this.resourceUrl, postMetaData, { observe: 'response' });
  }

  update(postMetaData: IPostMetaData): Observable<EntityResponseType> {
    return this.http.put<IPostMetaData>(this.resourceUrl, postMetaData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostMetaData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostMetaData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
