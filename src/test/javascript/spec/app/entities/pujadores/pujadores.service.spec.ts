import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PujadoresService } from 'app/entities/pujadores/pujadores.service';
import { IPujadores, Pujadores } from 'app/shared/model/pujadores.model';
import { EstadoPujadores } from 'app/shared/model/enumerations/estado-pujadores.model';

describe('Service Tests', () => {
  describe('Pujadores Service', () => {
    let injector: TestBed;
    let service: PujadoresService;
    let httpMock: HttpTestingController;
    let elemDefault: IPujadores;
    let expectedResult: IPujadores | IPujadores[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PujadoresService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Pujadores(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', EstadoPujadores.ACTIVO, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pujadores', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Pujadores()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pujadores', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            nroconsignacion: 'BBBBBB',
            nombrebanco: 'BBBBBB',
            estado: 'BBBBBB',
            pagoAceptado: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pujadores', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            nroconsignacion: 'BBBBBB',
            nombrebanco: 'BBBBBB',
            estado: 'BBBBBB',
            pagoAceptado: true
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

      it('should delete a Pujadores', () => {
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
