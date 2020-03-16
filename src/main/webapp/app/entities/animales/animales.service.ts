import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimales } from 'app/shared/model/animales.model';

type EntityResponseType = HttpResponse<IAnimales>;
type EntityArrayResponseType = HttpResponse<IAnimales[]>;

@Injectable({ providedIn: 'root' })
export class AnimalesService {
  public resourceUrl = SERVER_API_URL + 'api/animales';

  constructor(protected http: HttpClient) {}

  create(animales: IAnimales): Observable<EntityResponseType> {
    return this.http.post<IAnimales>(this.resourceUrl, animales, { observe: 'response' });
  }

  update(animales: IAnimales): Observable<EntityResponseType> {
    return this.http.put<IAnimales>(this.resourceUrl, animales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnimales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnimales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
