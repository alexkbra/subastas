import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { SubastasTestModule } from '../../../test.module';
import { LotesToAnimalesComponent } from 'app/entities/lotes-to-animales/lotes-to-animales.component';
import { LotesToAnimalesService } from 'app/entities/lotes-to-animales/lotes-to-animales.service';
import { LotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

describe('Component Tests', () => {
  describe('LotesToAnimales Management Component', () => {
    let comp: LotesToAnimalesComponent;
    let fixture: ComponentFixture<LotesToAnimalesComponent>;
    let service: LotesToAnimalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [LotesToAnimalesComponent],
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
        .overrideTemplate(LotesToAnimalesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LotesToAnimalesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LotesToAnimalesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LotesToAnimales(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lotesToAnimales && comp.lotesToAnimales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LotesToAnimales(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lotesToAnimales && comp.lotesToAnimales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
