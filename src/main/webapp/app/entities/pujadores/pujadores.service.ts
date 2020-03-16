import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPujadores } from 'app/shared/model/pujadores.model';

type EntityResponseType = HttpResponse<IPujadores>;
type EntityArrayResponseType = HttpResponse<IPujadores[]>;

@Injectable({ providedIn: 'root' })
export class PujadoresService {
  public resourceUrl = SERVER_API_URL + 'api/pujadores';

  constructor(protected http: HttpClient) {}

  create(pujadores: IPujadores): Observable<EntityResponseType> {
    return this.http.post<IPujadores>(this.resourceUrl, pujadores, { observe: 'response' });
  }

  update(pujadores: IPujadores): Observable<EntityResponseType> {
    return this.http.put<IPujadores>(this.resourceUrl, pujadores, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPujadores>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPujadores[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
