import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';
import { IPropietario } from 'app/shared/model/propietario.model';
import { IClasificacionLote } from 'app/shared/model/clasificacion-lote.model';
import { ISubastas } from 'app/shared/model/subastas.model';

export interface ILotes {
  id?: number;
  numero?: string;
  nombre?: string;
  decripcion?: any;
  raza?: string;
  cantidadAnimales?: number;
  pesopromedio?: number;
  pesototallote?: number;
  pesobaseporkg?: number;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  videoUrl?: string;
  idciudad?: number;
  lotestoanimales?: ILotesToAnimales[];
  propietario?: IPropietario;
  clasificacionlote?: IClasificacionLote;
  subastas?: ISubastas;
}

export class Lotes implements ILotes {
  constructor(
    public id?: number,
    public numero?: string,
    public nombre?: string,
    public decripcion?: any,
    public raza?: string,
    public cantidadAnimales?: number,
    public pesopromedio?: number,
    public pesototallote?: number,
    public pesobaseporkg?: number,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public videoUrl?: string,
    public idciudad?: number,
    public lotestoanimales?: ILotesToAnimales[],
    public propietario?: IPropietario,
    public clasificacionlote?: IClasificacionLote,
    public subastas?: ISubastas
  ) {}
}
