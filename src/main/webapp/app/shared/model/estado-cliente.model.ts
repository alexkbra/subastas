import { ICliente } from 'app/shared/model/cliente.model';

export interface IEstadoCliente {
  id?: number;
  nombre?: string;
  code?: string;
  clientes?: ICliente[];
}

export class EstadoCliente implements IEstadoCliente {
  constructor(public id?: number, public nombre?: string, public code?: string, public clientes?: ICliente[]) {}
}
