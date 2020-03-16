import { TipoContenido } from 'app/shared/model/enumerations/tipo-contenido.model';
import { EntidadContenido } from 'app/shared/model/enumerations/entidad-contenido.model';

export interface IContenido {
  id?: number;
  tipo?: TipoContenido;
  url?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  texto?: any;
  entidad?: EntidadContenido;
  idEntidad?: string;
}

export class Contenido implements IContenido {
  constructor(
    public id?: number,
    public tipo?: TipoContenido,
    public url?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public texto?: any,
    public entidad?: EntidadContenido,
    public idEntidad?: string
  ) {}
}
