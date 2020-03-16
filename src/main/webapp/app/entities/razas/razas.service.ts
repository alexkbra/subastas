import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRazas } from 'app/shared/model/razas.model';

type EntityResponseType = HttpResponse<IRazas>;
type EntityArrayResponseType = HttpResponse<IRazas[]>;

@Injectable({ providedIn: 'root' })
export class RazasService {
  public resourceUrl = SERVER_API_URL + 'api/razas';

  constructor(protected http: HttpClient) {}

  create(razas: IRazas): Observable<EntityResponseType> {
    return this.http.post<IRazas>(this.resourceUrl, razas, { observe: 'response' });
  }

  update(razas: IRazas): Observable<EntityResponseType> {
    return this.http.put<IRazas>(this.resourceUrl, razas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRazas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRazas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
