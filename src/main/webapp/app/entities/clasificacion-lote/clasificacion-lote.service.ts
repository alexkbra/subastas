import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

type EntityResponseType = HttpResponse<IClasificacionLote>;
type EntityArrayResponseType = HttpResponse<IClasificacionLote[]>;

@Injectable({ providedIn: 'root' })
export class ClasificacionLoteService {
  public resourceUrl = SERVER_API_URL + 'api/clasificacion-lotes';

  constructor(protected http: HttpClient) {}

  create(clasificacionLote: IClasificacionLote): Observable<EntityResponseType> {
    return this.http.post<IClasificacionLote>(this.resourceUrl, clasificacionLote, { observe: 'response' });
  }

  update(clasificacionLote: IClasificacionLote): Observable<EntityResponseType> {
    return this.http.put<IClasificacionLote>(this.resourceUrl, clasificacionLote, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClasificacionLote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClasificacionLote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
