import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DispositivoService } from 'app/entities/dispositivo/dispositivo.service';
import { IDispositivo, Dispositivo } from 'app/shared/model/dispositivo.model';

describe('Service Tests', () => {
  describe('Dispositivo Service', () => {
    let injector: TestBed;
    let service: DispositivoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDispositivo;
    let expectedResult: IDispositivo | IDispositivo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DispositivoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Dispositivo(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Dispositivo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Dispositivo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dispositivo', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            idusuario: 'BBBBBB',
            idcliente: 'BBBBBB',
            activo: true,
            dispositivo: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Dispositivo', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            idusuario: 'BBBBBB',
            idcliente: 'BBBBBB',
            activo: true,
            dispositivo: 'BBBBBB'
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

      it('should delete a Dispositivo', () => {
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
