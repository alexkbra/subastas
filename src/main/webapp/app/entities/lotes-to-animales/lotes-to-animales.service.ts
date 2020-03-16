import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

type EntityResponseType = HttpResponse<ILotesToAnimales>;
type EntityArrayResponseType = HttpResponse<ILotesToAnimales[]>;

@Injectable({ providedIn: 'root' })
export class LotesToAnimalesService {
  public resourceUrl = SERVER_API_URL + 'api/lotes-to-animales';

  constructor(protected http: HttpClient) {}

  create(lotesToAnimales: ILotesToAnimales): Observable<EntityResponseType> {
    return this.http.post<ILotesToAnimales>(this.resourceUrl, lotesToAnimales, { observe: 'response' });
  }

  update(lotesToAnimales: ILotesToAnimales): Observable<EntityResponseType> {
    return this.http.put<ILotesToAnimales>(this.resourceUrl, lotesToAnimales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILotesToAnimales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILotesToAnimales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
