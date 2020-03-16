import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MensajesService } from 'app/entities/mensajes/mensajes.service';
import { IMensajes, Mensajes } from 'app/shared/model/mensajes.model';
import { TipoMensaje } from 'app/shared/model/enumerations/tipo-mensaje.model';
import { TipoCliente } from 'app/shared/model/enumerations/tipo-cliente.model';

describe('Service Tests', () => {
  describe('Mensajes Service', () => {
    let injector: TestBed;
    let service: MensajesService;
    let httpMock: HttpTestingController;
    let elemDefault: IMensajes;
    let expectedResult: IMensajes | IMensajes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MensajesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Mensajes(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        TipoMensaje.GANDOR,
        TipoCliente.COMPRADOR,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Mensajes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Mensajes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mensajes', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            cuerpo: 'BBBBBB',
            numeroLote: 'BBBBBB',
            tipoMensaje: 'BBBBBB',
            tipoCliente: 'BBBBBB',
            direccion: 'BBBBBB',
            idusuario: 'BBBBBB',
            idcliente: 'BBBBBB',
            activo: true,
            valorPagar: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mensajes', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            cuerpo: 'BBBBBB',
            numeroLote: 'BBBBBB',
            tipoMensaje: 'BBBBBB',
            tipoCliente: 'BBBBBB',
            direccion: 'BBBBBB',
            idusuario: 'BBBBBB',
            idcliente: 'BBBBBB',
            activo: true,
            valorPagar: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mensajes', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
