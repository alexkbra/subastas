import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AnimalesService } from 'app/entities/animales/animales.service';
import { IAnimales, Animales } from 'app/shared/model/animales.model';
import { Sexos } from 'app/shared/model/enumerations/sexos.model';

describe('Service Tests', () => {
  describe('Animales Service', () => {
    let injector: TestBed;
    let service: AnimalesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimales;
    let expectedResult: IAnimales | IAnimales[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Animales(0, 0, 'AAAAAAA', Sexos.MASCULINO, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Animales', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Animales()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Animales', () => {
        const returnedFromService = Object.assign(
          {
            pesokg: 1,
            descripcion: 'BBBBBB',
            sexo: 'BBBBBB',
            procedencia: 'BBBBBB',
            propietario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Animales', () => {
        const returnedFromService = Object.assign(
          {
            pesokg: 1,
            descripcion: 'BBBBBB',
            sexo: 'BBBBBB',
            procedencia: 'BBBBBB',
            propietario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            videoUrl: 'BBBBBB'
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

      it('should delete a Animales', () => {
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
