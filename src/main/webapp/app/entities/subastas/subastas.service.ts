import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubastas } from 'app/shared/model/subastas.model';

type EntityResponseType = HttpResponse<ISubastas>;
type EntityArrayResponseType = HttpResponse<ISubastas[]>;

@Injectable({ providedIn: 'root' })
export class SubastasService {
  public resourceUrl = SERVER_API_URL + 'api/subastas';

  constructor(protected http: HttpClient) {}

  create(subastas: ISubastas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subastas);
    return this.http
      .post<ISubastas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subastas: ISubastas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subastas);
    return this.http
      .put<ISubastas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubastas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubastas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subastas: ISubastas): ISubastas {
    const copy: ISubastas = Object.assign({}, subastas, {
      fechainicio: subastas.fechainicio && subastas.fechainicio.isValid() ? subastas.fechainicio.toJSON() : undefined,
      fechafinal: subastas.fechafinal && subastas.fechafinal.isValid() ? subastas.fechafinal.toJSON() : undefined,
      fechacreacion: subastas.fechacreacion && subastas.fechacreacion.isValid() ? subastas.fechacreacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechainicio = res.body.fechainicio ? moment(res.body.fechainicio) : undefined;
      res.body.fechafinal = res.body.fechafinal ? moment(res.body.fechafinal) : undefined;
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subastas: ISubastas) => {
        subastas.fechainicio = subastas.fechainicio ? moment(subastas.fechainicio) : undefined;
        subastas.fechafinal = subastas.fechafinal ? moment(subastas.fechafinal) : undefined;
        subastas.fechacreacion = subastas.fechacreacion ? moment(subastas.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
