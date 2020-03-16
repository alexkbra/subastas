import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoCliente } from 'app/shared/model/estado-cliente.model';

type EntityResponseType = HttpResponse<IEstadoCliente>;
type EntityArrayResponseType = HttpResponse<IEstadoCliente[]>;

@Injectable({ providedIn: 'root' })
export class EstadoClienteService {
  public resourceUrl = SERVER_API_URL + 'api/estado-clientes';

  constructor(protected http: HttpClient) {}

  create(estadoCliente: IEstadoCliente): Observable<EntityResponseType> {
    return this.http.post<IEstadoCliente>(this.resourceUrl, estadoCliente, { observe: 'response' });
  }

  update(estadoCliente: IEstadoCliente): Observable<EntityResponseType> {
    return this.http.put<IEstadoCliente>(this.resourceUrl, estadoCliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoCliente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
