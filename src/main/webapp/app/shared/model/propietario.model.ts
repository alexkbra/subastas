import { ILotes } from 'app/shared/model/lotes.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';

export interface IPropietario {
  id?: number;
  numeroDocumento?: string;
  nombreORazonSocial?: string;
  correo?: string;
  telefonocelular?: string;
  telefonofijo?: string;
  telefonoempresarial?: string;
  direccionresidencial?: string;
  direccionempresarial?: string;
  idusuario?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  idciudad?: number;
  lotes?: ILotes[];
  tipoDocumento?: ITipoDocumento;
}

export class Propietario implements IPropietario {
  constructor(
    public id?: number,
    public numeroDocumento?: string,
    public nombreORazonSocial?: string,
    public correo?: string,
    public telefonocelular?: string,
    public telefonofijo?: string,
    public telefonoempresarial?: string,
    public direccionresidencial?: string,
    public direccionempresarial?: string,
    public idusuario?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public idciudad?: number,
    public lotes?: ILotes[],
    public tipoDocumento?: ITipoDocumento
  ) {}
}
