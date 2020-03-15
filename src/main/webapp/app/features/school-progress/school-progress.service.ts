import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISchoolProgress } from 'app/shared/model/school-progress.model';

type EntityResponseType = HttpResponse<ISchoolProgress>;
type EntityArrayResponseType = HttpResponse<ISchoolProgress[]>;

@Injectable({ providedIn: 'root' })
export class SchoolProgressService {
  public resourceUrl = SERVER_API_URL + 'api/school-progresses';

  constructor(protected http: HttpClient) {}

  create(schoolProgress: ISchoolProgress): Observable<EntityResponseType> {
    return this.http.post<ISchoolProgress>(this.resourceUrl, schoolProgress, { observe: 'response' });
  }

  update(schoolProgress: ISchoolProgress): Observable<EntityResponseType> {
    return this.http.put<ISchoolProgress>(this.resourceUrl, schoolProgress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchoolProgress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchoolProgress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
