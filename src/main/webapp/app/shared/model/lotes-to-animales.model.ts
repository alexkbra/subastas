import { ILotes } from 'app/shared/model/lotes.model';
import { IAnimales } from 'app/shared/model/animales.model';

export interface ILotesToAnimales {
  id?: number;
  cantidad?: number;
  lotes?: ILotes;
  animales?: IAnimales;
}

export class LotesToAnimales implements ILotesToAnimales {
  constructor(public id?: number, public cantidad?: number, public lotes?: ILotes, public animales?: IAnimales) {}
}
