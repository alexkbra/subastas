import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { SubastasTestModule } from '../../../test.module';
import { ClasificacionLoteComponent } from 'app/entities/clasificacion-lote/clasificacion-lote.component';
import { ClasificacionLoteService } from 'app/entities/clasificacion-lote/clasificacion-lote.service';
import { ClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

describe('Component Tests', () => {
  describe('ClasificacionLote Management Component', () => {
    let comp: ClasificacionLoteComponent;
    let fixture: ComponentFixture<ClasificacionLoteComponent>;
    let service: ClasificacionLoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [ClasificacionLoteComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ClasificacionLoteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClasificacionLoteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasificacionLoteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClasificacionLote(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.clasificacionLotes && comp.clasificacionLotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClasificacionLote(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.clasificacionLotes && comp.clasificacionLotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
