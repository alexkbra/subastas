import { IMunicipios } from 'app/shared/model/municipios.model';

export interface IDepartamentos {
  id?: number;
  iddepartamentos?: number;
  departamento?: string;
  municipios?: IMunicipios[];
}

export class Departamentos implements IDepartamentos {
  constructor(public id?: number, public iddepartamentos?: number, public departamento?: string, public municipios?: IMunicipios[]) {}
}
