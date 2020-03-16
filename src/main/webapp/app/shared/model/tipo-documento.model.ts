import { IPropietario } from 'app/shared/model/propietario.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface ITipoDocumento {
  id?: number;
  codigo?: string;
  nombre?: string;
  propietarios?: IPropietario[];
  clientes?: ICliente[];
}

export class TipoDocumento implements ITipoDocumento {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombre?: string,
    public propietarios?: IPropietario[],
    public clientes?: ICliente[]
  ) {}
}
