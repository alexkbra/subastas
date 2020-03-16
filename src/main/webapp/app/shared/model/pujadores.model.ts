import { IPujas } from 'app/shared/model/pujas.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { EstadoPujadores } from 'app/shared/model/enumerations/estado-pujadores.model';

export interface IPujadores {
  id?: number;
  idEvento?: string;
  idSubasta?: string;
  idLote?: string;
  nroconsignacion?: string;
  nombrebanco?: string;
  estado?: EstadoPujadores;
  pagoAceptado?: boolean;
  pujas?: IPujas[];
  cliente?: ICliente;
}

export class Pujadores implements IPujadores {
  constructor(
    public id?: number,
    public idEvento?: string,
    public idSubasta?: string,
    public idLote?: string,
    public nroconsignacion?: string,
    public nombrebanco?: string,
    public estado?: EstadoPujadores,
    public pagoAceptado?: boolean,
    public pujas?: IPujas[],
    public cliente?: ICliente
  ) {
    this.pagoAceptado = this.pagoAceptado || false;
  }
}
