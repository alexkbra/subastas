import { Moment } from 'moment';
import { ILotes } from 'app/shared/model/lotes.model';
import { IEventos } from 'app/shared/model/eventos.model';

export interface ISubastas {
  id?: number;
  nombre?: string;
  decripcion?: any;
  fechainicio?: Moment;
  fechafinal?: Moment;
  timpoRecloGanador?: number;
  fechacreacion?: Moment;
  valorinicial?: number;
  valoractual?: number;
  valortope?: number;
  pagaAnticipo?: boolean;
  pesobaseporkg?: number;
  pesototallote?: number;
  valorAnticipo?: number;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  videoUrl?: string;
  estadoActivo?: boolean;
  estadoGanador?: boolean;
  lotes?: ILotes[];
  eventos?: IEventos;
}

export class Subastas implements ISubastas {
  constructor(
    public id?: number,
    public nombre?: string,
    public decripcion?: any,
    public fechainicio?: Moment,
    public fechafinal?: Moment,
    public timpoRecloGanador?: number,
    public fechacreacion?: Moment,
    public valorinicial?: number,
    public valoractual?: number,
    public valortope?: number,
    public pagaAnticipo?: boolean,
    public pesobaseporkg?: number,
    public pesototallote?: number,
    public valorAnticipo?: number,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public videoUrl?: string,
    public estadoActivo?: boolean,
    public estadoGanador?: boolean,
    public lotes?: ILotes[],
    public eventos?: IEventos
  ) {
    this.pagaAnticipo = this.pagaAnticipo || false;
    this.estadoActivo = this.estadoActivo || false;
    this.estadoGanador = this.estadoGanador || false;
  }
}
