import { IRazas } from 'app/shared/model/razas.model';

export interface IEspecies {
  id?: number;
  nombre?: string;
  decripcion?: any;
  code?: string;
  razas?: IRazas[];
}

export class Especies implements IEspecies {
  constructor(public id?: number, public nombre?: string, public decripcion?: any, public code?: string, public razas?: IRazas[]) {}
}
