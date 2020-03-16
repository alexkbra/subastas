import { IAnimales } from 'app/shared/model/animales.model';
import { IEspecies } from 'app/shared/model/especies.model';

export interface IRazas {
  id?: number;
  nombre?: string;
  decripcion?: any;
  code?: string;
  animales?: IAnimales[];
  especies?: IEspecies;
}

export class Razas implements IRazas {
  constructor(
    public id?: number,
    public nombre?: string,
    public decripcion?: any,
    public code?: string,
    public animales?: IAnimales[],
    public especies?: IEspecies
  ) {}
}
