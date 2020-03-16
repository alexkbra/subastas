import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventos } from 'app/shared/model/eventos.model';

type EntityResponseType = HttpResponse<IEventos>;
type EntityArrayResponseType = HttpResponse<IEventos[]>;

@Injectable({ providedIn: 'root' })
export class EventosService {
  public resourceUrl = SERVER_API_URL + 'api/eventos';

  constructor(protected http: HttpClient) {}

  create(eventos: IEventos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventos);
    return this.http
      .post<IEventos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eventos: IEventos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventos);
    return this.http
      .put<IEventos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEventos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEventos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(eventos: IEventos): IEventos {
    const copy: IEventos = Object.assign({}, eventos, {
      fechainicio: eventos.fechainicio && eventos.fechainicio.isValid() ? eventos.fechainicio.toJSON() : undefined,
      fechafinal: eventos.fechafinal && eventos.fechafinal.isValid() ? eventos.fechafinal.toJSON() : undefined,
      fechacreacion: eventos.fechacreacion && eventos.fechacreacion.isValid() ? eventos.fechacreacion.toJSON() : undefined
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
      res.body.forEach((eventos: IEventos) => {
        eventos.fechainicio = eventos.fechainicio ? moment(eventos.fechainicio) : undefined;
        eventos.fechafinal = eventos.fechafinal ? moment(eventos.fechafinal) : undefined;
        eventos.fechacreacion = eventos.fechacreacion ? moment(eventos.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
