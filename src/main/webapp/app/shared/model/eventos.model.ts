import { Moment } from 'moment';
import { ISubastas } from 'app/shared/model/subastas.model';

export interface IEventos {
  id?: number;
  nombre?: string;
  decripcion?: any;
  fechainicio?: Moment;
  fechafinal?: Moment;
  fechacreacion?: Moment;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  videoUrl?: string;
  idciudad?: number;
  latitud?: string;
  longitud?: string;
  estadoActivo?: boolean;
  subastas?: ISubastas[];
}

export class Eventos implements IEventos {
  constructor(
    public id?: number,
    public nombre?: string,
    public decripcion?: any,
    public fechainicio?: Moment,
    public fechafinal?: Moment,
    public fechacreacion?: Moment,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public videoUrl?: string,
    public idciudad?: number,
    public latitud?: string,
    public longitud?: string,
    public estadoActivo?: boolean,
    public subastas?: ISubastas[]
  ) {
    this.estadoActivo = this.estadoActivo || false;
  }
}
