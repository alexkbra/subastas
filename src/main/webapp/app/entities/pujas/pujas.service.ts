import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPujas } from 'app/shared/model/pujas.model';

type EntityResponseType = HttpResponse<IPujas>;
type EntityArrayResponseType = HttpResponse<IPujas[]>;

@Injectable({ providedIn: 'root' })
export class PujasService {
  public resourceUrl = SERVER_API_URL + 'api/pujas';

  constructor(protected http: HttpClient) {}

  create(pujas: IPujas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pujas);
    return this.http
      .post<IPujas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pujas: IPujas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pujas);
    return this.http
      .put<IPujas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPujas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPujas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pujas: IPujas): IPujas {
    const copy: IPujas = Object.assign({}, pujas, {
      fechacreacion: pujas.fechacreacion && pujas.fechacreacion.isValid() ? pujas.fechacreacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pujas: IPujas) => {
        pujas.fechacreacion = pujas.fechacreacion ? moment(pujas.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
