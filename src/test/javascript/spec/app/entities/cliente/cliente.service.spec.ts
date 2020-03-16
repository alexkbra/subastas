import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';

describe('Service Tests', () => {
  describe('Cliente Service', () => {
    let injector: TestBed;
    let service: ClienteService;
    let httpMock: HttpTestingController;
    let elemDefault: ICliente;
    let expectedResult: ICliente | ICliente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ClienteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Cliente(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechanacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Cliente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechanacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechanacimiento: currentDate
          },
          returnedFromService
        );

        service.create(new Cliente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cliente', () => {
        const returnedFromService = Object.assign(
          {
            numeroDocumento: 1,
            nombre: 'BBBBBB',
            apellido: 'BBBBBB',
            correo: 'BBBBBB',
            nombrerepresentantelegal: 'BBBBBB',
            telefonocelular: 'BBBBBB',
            telefonofijo: 'BBBBBB',
            telefonoempresarial: 'BBBBBB',
            telefonorepresentantelegal: 'BBBBBB',
            direccionresidencial: 'BBBBBB',
            direccionempresarial: 'BBBBBB',
            direccionrepresentantelegal: 'BBBBBB',
            fechanacimiento: currentDate.format(DATE_FORMAT),
            idusuario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            idciudad: 1,
            anonimo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechanacimiento: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Cliente', () => {
        const returnedFromService = Object.assign(
          {
            numeroDocumento: 1,
            nombre: 'BBBBBB',
            apellido: 'BBBBBB',
            correo: 'BBBBBB',
            nombrerepresentantelegal: 'BBBBBB',
            telefonocelular: 'BBBBBB',
            telefonofijo: 'BBBBBB',
            telefonoempresarial: 'BBBBBB',
            telefonorepresentantelegal: 'BBBBBB',
            direccionresidencial: 'BBBBBB',
            direccionempresarial: 'BBBBBB',
            direccionrepresentantelegal: 'BBBBBB',
            fechanacimiento: currentDate.format(DATE_FORMAT),
            idusuario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            idciudad: 1,
            anonimo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechanacimiento: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cliente', () => {
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
