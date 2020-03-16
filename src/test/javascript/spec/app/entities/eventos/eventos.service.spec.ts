import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EventosService } from 'app/entities/eventos/eventos.service';
import { IEventos, Eventos } from 'app/shared/model/eventos.model';

describe('Service Tests', () => {
  describe('Eventos Service', () => {
    let injector: TestBed;
    let service: EventosService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventos;
    let expectedResult: IEventos | IEventos[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Eventos(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        0,
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

      it('should create a Eventos', () => {
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

        service.create(new Eventos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Eventos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            idciudad: 1,
            latitud: 'BBBBBB',
            longitud: 'BBBBBB',
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

      it('should return a list of Eventos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            fechainicio: currentDate.format(DATE_TIME_FORMAT),
            fechafinal: currentDate.format(DATE_TIME_FORMAT),
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            idciudad: 1,
            latitud: 'BBBBBB',
            longitud: 'BBBBBB',
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

      it('should delete a Eventos', () => {
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
