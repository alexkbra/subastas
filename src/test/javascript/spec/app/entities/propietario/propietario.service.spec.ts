import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PropietarioService } from 'app/entities/propietario/propietario.service';
import { IPropietario, Propietario } from 'app/shared/model/propietario.model';

describe('Service Tests', () => {
  describe('Propietario Service', () => {
    let injector: TestBed;
    let service: PropietarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IPropietario;
    let expectedResult: IPropietario | IPropietario[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PropietarioService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Propietario(
        0,
        '11111',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
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

      it('should create a Propietario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Propietario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Propietario', () => {
        const returnedFromService = Object.assign(
          {
            numeroDocumento: '11111',
            nombreORazonSocial: 'BBBBBB',
            correo: 'BBBBBB',
            telefonocelular: 'BBBBBB',
            telefonofijo: 'BBBBBB',
            telefonoempresarial: 'BBBBBB',
            direccionresidencial: 'BBBBBB',
            direccionempresarial: 'BBBBBB',
            idusuario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
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

      it('should return a list of Propietario', () => {
        const returnedFromService = Object.assign(
          {
            numeroDocumento: '11111',
            nombreORazonSocial: 'BBBBBB',
            correo: 'BBBBBB',
            telefonocelular: 'BBBBBB',
            telefonofijo: 'BBBBBB',
            telefonoempresarial: 'BBBBBB',
            direccionresidencial: 'BBBBBB',
            direccionempresarial: 'BBBBBB',
            idusuario: 'BBBBBB',
            imagenUrl: 'BBBBBB',
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

      it('should delete a Propietario', () => {
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
