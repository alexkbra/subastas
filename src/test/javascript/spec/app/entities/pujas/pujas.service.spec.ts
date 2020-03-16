import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PujasService } from 'app/entities/pujas/pujas.service';
import { IPujas, Pujas } from 'app/shared/model/pujas.model';

describe('Service Tests', () => {
  describe('Pujas Service', () => {
    let injector: TestBed;
    let service: PujasService;
    let httpMock: HttpTestingController;
    let elemDefault: IPujas;
    let expectedResult: IPujas | IPujas[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PujasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pujas(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pujas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.create(new Pujas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pujas', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            valor: 1,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            aceptadoGanador: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pujas', () => {
        const returnedFromService = Object.assign(
          {
            idEvento: 'BBBBBB',
            idSubasta: 'BBBBBB',
            idLote: 'BBBBBB',
            valor: 1,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            aceptadoGanador: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pujas', () => {
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
