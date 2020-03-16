import { TipoMensaje } from 'app/shared/model/enumerations/tipo-mensaje.model';
import { TipoCliente } from 'app/shared/model/enumerations/tipo-cliente.model';

export interface IMensajes {
  id?: number;
  titulo?: string;
  cuerpo?: any;
  numeroLote?: string;
  tipoMensaje?: TipoMensaje;
  tipoCliente?: TipoCliente;
  direccion?: string;
  idusuario?: string;
  idcliente?: string;
  activo?: boolean;
  valorPagar?: number;
}

export class Mensajes implements IMensajes {
  constructor(
    public id?: number,
    public titulo?: string,
    public cuerpo?: any,
    public numeroLote?: string,
    public tipoMensaje?: TipoMensaje,
    public tipoCliente?: TipoCliente,
    public direccion?: string,
    public idusuario?: string,
    public idcliente?: string,
    public activo?: boolean,
    public valorPagar?: number
  ) {
    this.activo = this.activo || false;
  }
}
