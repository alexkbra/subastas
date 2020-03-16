import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LotesService } from 'app/entities/lotes/lotes.service';
import { ILotes, Lotes } from 'app/shared/model/lotes.model';

describe('Service Tests', () => {
  describe('Lotes Service', () => {
    let injector: TestBed;
    let service: LotesService;
    let httpMock: HttpTestingController;
    let elemDefault: ILotes;
    let expectedResult: ILotes | ILotes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LotesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Lotes(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 'image/png', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Lotes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Lotes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lotes', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            raza: 'BBBBBB',
            cantidadAnimales: 1,
            pesopromedio: 1,
            pesototallote: 1,
            pesobaseporkg: 1,
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            idciudad: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lotes', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            nombre: 'BBBBBB',
            decripcion: 'BBBBBB',
            raza: 'BBBBBB',
            cantidadAnimales: 1,
            pesopromedio: 1,
            pesototallote: 1,
            pesobaseporkg: 1,
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            idciudad: 1
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

      it('should delete a Lotes', () => {
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
