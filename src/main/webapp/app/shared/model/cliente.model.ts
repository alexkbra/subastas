import { Moment } from 'moment';
import { IPujadores } from 'app/shared/model/pujadores.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { IEstadoCliente } from 'app/shared/model/estado-cliente.model';

export interface ICliente {
  id?: number;
  numeroDocumento?: number;
  nombre?: string;
  apellido?: string;
  correo?: string;
  nombrerepresentantelegal?: string;
  telefonocelular?: string;
  telefonofijo?: string;
  telefonoempresarial?: string;
  telefonorepresentantelegal?: string;
  direccionresidencial?: string;
  direccionempresarial?: string;
  direccionrepresentantelegal?: string;
  fechanacimiento?: Moment;
  idusuario?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  idciudad?: number;
  anonimo?: boolean;
  pujadores?: IPujadores[];
  tipoDocumento?: ITipoDocumento;
  estadocliente?: IEstadoCliente;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public numeroDocumento?: number,
    public nombre?: string,
    public apellido?: string,
    public correo?: string,
    public nombrerepresentantelegal?: string,
    public telefonocelular?: string,
    public telefonofijo?: string,
    public telefonoempresarial?: string,
    public telefonorepresentantelegal?: string,
    public direccionresidencial?: string,
    public direccionempresarial?: string,
    public direccionrepresentantelegal?: string,
    public fechanacimiento?: Moment,
    public idusuario?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public idciudad?: number,
    public anonimo?: boolean,
    public pujadores?: IPujadores[],
    public tipoDocumento?: ITipoDocumento,
    public estadocliente?: IEstadoCliente
  ) {
    this.anonimo = this.anonimo || false;
  }
}
