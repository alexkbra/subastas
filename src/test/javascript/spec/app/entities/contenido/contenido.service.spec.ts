import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ContenidoService } from 'app/entities/contenido/contenido.service';
import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { TipoContenido } from 'app/shared/model/enumerations/tipo-contenido.model';
import { EntidadContenido } from 'app/shared/model/enumerations/entidad-contenido.model';

describe('Service Tests', () => {
  describe('Contenido Service', () => {
    let injector: TestBed;
    let service: ContenidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContenido;
    let expectedResult: IContenido | IContenido[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContenidoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Contenido(
        0,
        TipoContenido.VIDEO,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        EntidadContenido.Cliente,
        'AAAAAAA'
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

      it('should create a Contenido', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Contenido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contenido', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            url: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            texto: 'BBBBBB',
            entidad: 'BBBBBB',
            idEntidad: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Contenido', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            url: 'BBBBBB',
            imagenUrl: 'BBBBBB',
            texto: 'BBBBBB',
            entidad: 'BBBBBB',
            idEntidad: 'BBBBBB'
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

      it('should delete a Contenido', () => {
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
