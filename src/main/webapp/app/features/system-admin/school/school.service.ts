import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISchool } from 'app/shared/model/school.model';

type EntityResponseType = HttpResponse<ISchool>;
type EntityArrayResponseType = HttpResponse<ISchool[]>;

@Injectable({ providedIn: 'root' })
export class SchoolService {
  public resourceUrl = SERVER_API_URL + 'api/schools';

  constructor(protected http: HttpClient) {}

  create(school: ISchool): Observable<EntityResponseType> {
    return this.http.post<ISchool>(this.resourceUrl, school, { observe: 'response' });
  }

  update(school: ISchool): Observable<EntityResponseType> {
    return this.http.put<ISchool>(this.resourceUrl, school, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchool>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchool[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
