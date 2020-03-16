import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartamentos } from 'app/shared/model/departamentos.model';

type EntityResponseType = HttpResponse<IDepartamentos>;
type EntityArrayResponseType = HttpResponse<IDepartamentos[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentosService {
  public resourceUrl = SERVER_API_URL + 'api/departamentos';

  constructor(protected http: HttpClient) {}

  create(departamentos: IDepartamentos): Observable<EntityResponseType> {
    return this.http.post<IDepartamentos>(this.resourceUrl, departamentos, { observe: 'response' });
  }

  update(departamentos: IDepartamentos): Observable<EntityResponseType> {
    return this.http.put<IDepartamentos>(this.resourceUrl, departamentos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartamentos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
