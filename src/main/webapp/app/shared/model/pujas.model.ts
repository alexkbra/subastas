import { Moment } from 'moment';
import { IPujadores } from 'app/shared/model/pujadores.model';

export interface IPujas {
  id?: number;
  idEvento?: string;
  idSubasta?: string;
  idLote?: string;
  valor?: number;
  fechacreacion?: Moment;
  aceptadoGanador?: boolean;
  pujadores?: IPujadores;
}

export class Pujas implements IPujas {
  constructor(
    public id?: number,
    public idEvento?: string,
    public idSubasta?: string,
    public idLote?: string,
    public valor?: number,
    public fechacreacion?: Moment,
    public aceptadoGanador?: boolean,
    public pujadores?: IPujadores
  ) {
    this.aceptadoGanador = this.aceptadoGanador || false;
  }
}
