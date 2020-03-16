import { ILotes } from 'app/shared/model/lotes.model';

export interface IClasificacionLote {
  id?: number;
  nombre?: string;
  code?: string;
  lotes?: ILotes[];
}

export class ClasificacionLote implements IClasificacionLote {
  constructor(public id?: number, public nombre?: string, public code?: string, public lotes?: ILotes[]) {}
}
