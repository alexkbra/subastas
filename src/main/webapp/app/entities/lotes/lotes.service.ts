import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILotes } from 'app/shared/model/lotes.model';

type EntityResponseType = HttpResponse<ILotes>;
type EntityArrayResponseType = HttpResponse<ILotes[]>;

@Injectable({ providedIn: 'root' })
export class LotesService {
  public resourceUrl = SERVER_API_URL + 'api/lotes';

  constructor(protected http: HttpClient) {}

  create(lotes: ILotes): Observable<EntityResponseType> {
    return this.http.post<ILotes>(this.resourceUrl, lotes, { observe: 'response' });
  }

  update(lotes: ILotes): Observable<EntityResponseType> {
    return this.http.put<ILotes>(this.resourceUrl, lotes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILotes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILotes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
