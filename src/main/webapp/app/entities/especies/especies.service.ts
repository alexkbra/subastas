import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEspecies } from 'app/shared/model/especies.model';

type EntityResponseType = HttpResponse<IEspecies>;
type EntityArrayResponseType = HttpResponse<IEspecies[]>;

@Injectable({ providedIn: 'root' })
export class EspeciesService {
  public resourceUrl = SERVER_API_URL + 'api/especies';

  constructor(protected http: HttpClient) {}

  create(especies: IEspecies): Observable<EntityResponseType> {
    return this.http.post<IEspecies>(this.resourceUrl, especies, { observe: 'response' });
  }

  update(especies: IEspecies): Observable<EntityResponseType> {
    return this.http.put<IEspecies>(this.resourceUrl, especies, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEspecies>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEspecies[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
