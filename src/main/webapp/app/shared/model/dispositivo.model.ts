export interface IDispositivo {
  id?: number;
  idEvento?: string;
  idSubasta?: string;
  idLote?: string;
  idusuario?: string;
  idcliente?: string;
  activo?: boolean;
  dispositivo?: string;
}

export class Dispositivo implements IDispositivo {
  constructor(
    public id?: number,
    public idEvento?: string,
    public idSubasta?: string,
    public idLote?: string,
    public idusuario?: string,
    public idcliente?: string,
    public activo?: boolean,
    public dispositivo?: string
  ) {
    this.activo = this.activo || false;
  }
}
