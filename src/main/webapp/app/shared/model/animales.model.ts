import { ILotesToAnimales } from 'app/shared/model/lotes-to-animales.model';
import { IRazas } from 'app/shared/model/razas.model';
import { Sexos } from 'app/shared/model/enumerations/sexos.model';

export interface IAnimales {
  id?: number;
  pesokg?: number;
  descripcion?: any;
  sexo?: Sexos;
  procedencia?: string;
  propietario?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  videoUrl?: string;
  lotestoanimales?: ILotesToAnimales[];
  razas?: IRazas;
}

export class Animales implements IAnimales {
  constructor(
    public id?: number,
    public pesokg?: number,
    public descripcion?: any,
    public sexo?: Sexos,
    public procedencia?: string,
    public propietario?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public videoUrl?: string,
    public lotestoanimales?: ILotesToAnimales[],
    public razas?: IRazas
  ) {}
}
