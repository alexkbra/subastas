import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SubastasService } from 'app/entities/subastas/subastas.service';
import { ISubastas, Subastas } from 'app/shared/model/subastas.model';

describe('Service Tests', () => {
  describe('Subastas Service', () => {
    let injector: TestBed;
    let service: SubastasService;
    let httpMock: HttpTestingController;
    let elemDefault: ISubastas;
    let expectedResult: ISubastas | ISubastas[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SubastasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Subastas(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        currentDate,
        0,
        0,
        0,
        false,
        0,
        0,
        0,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Subastas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainicio: currentDate,
            fechafinal: currentDate,
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.create(new Subastas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Subastas', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            timpoRecloGanador: 'BBBBBB',
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            valorinicial: 1,
            valoractual: 1,
            valortope: 1,
            pagaAnticipo: true,
            pesobaseporkg: 1,
            pesototallote: 1,
            valorAnticipo: 1,
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            estadoActivo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainicio: currentDate,
            fechafinal: currentDate,
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Subastas', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            timpoRecloGanador: 'BBBBBB',
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            valorinicial: 1,
            valoractual: 1,
            valortope: 1,
            pagaAnticipo: true,
            pesobaseporkg: 1,
            pesototallote: 1,
            valorAnticipo: 1,
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            estadoActivo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainicio: currentDate,
            fechafinal: currentDate,
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

      it('should delete a Subastas', () => {
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
