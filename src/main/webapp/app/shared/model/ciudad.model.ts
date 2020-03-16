import { IMunicipios } from 'app/shared/model/municipios.model';

export interface ICiudad {
  id?: number;
  idciudad?: number;
  nombre?: string;
  municipios?: IMunicipios;
}

export class Ciudad implements ICiudad {
  constructor(public id?: number, public idciudad?: number, public nombre?: string, public municipios?: IMunicipios) {}
}
