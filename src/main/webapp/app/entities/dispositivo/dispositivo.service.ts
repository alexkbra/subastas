import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDispositivo } from 'app/shared/model/dispositivo.model';

type EntityResponseType = HttpResponse<IDispositivo>;
type EntityArrayResponseType = HttpResponse<IDispositivo[]>;

@Injectable({ providedIn: 'root' })
export class DispositivoService {
  public resourceUrl = SERVER_API_URL + 'api/dispositivos';

  constructor(protected http: HttpClient) {}

  create(dispositivo: IDispositivo): Observable<EntityResponseType> {
    return this.http.post<IDispositivo>(this.resourceUrl, dispositivo, { observe: 'response' });
  }

  update(dispositivo: IDispositivo): Observable<EntityResponseType> {
    return this.http.put<IDispositivo>(this.resourceUrl, dispositivo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDispositivo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDispositivo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
