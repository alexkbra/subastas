import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPropietario } from 'app/shared/model/propietario.model';

type EntityResponseType = HttpResponse<IPropietario>;
type EntityArrayResponseType = HttpResponse<IPropietario[]>;

@Injectable({ providedIn: 'root' })
export class PropietarioService {
  public resourceUrl = SERVER_API_URL + 'api/propietarios';

  constructor(protected http: HttpClient) {}

  create(propietario: IPropietario): Observable<EntityResponseType> {
    return this.http.post<IPropietario>(this.resourceUrl, propietario, { observe: 'response' });
  }

  update(propietario: IPropietario): Observable<EntityResponseType> {
    return this.http.put<IPropietario>(this.resourceUrl, propietario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPropietario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPropietario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
