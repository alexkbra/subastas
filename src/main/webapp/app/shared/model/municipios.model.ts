import { ICiudad } from 'app/shared/model/ciudad.model';
import { IDepartamentos } from 'app/shared/model/departamentos.model';

export interface IMunicipios {
  id?: number;
  idmunicipios?: number;
  municipio?: string;
  estado?: string;
  ciudads?: ICiudad[];
  departamentos?: IDepartamentos;
}

export class Municipios implements IMunicipios {
  constructor(
    public id?: number,
    public idmunicipios?: number,
    public municipio?: string,
    public estado?: string,
    public ciudads?: ICiudad[],
    public departamentos?: IDepartamentos
  ) {}
}
