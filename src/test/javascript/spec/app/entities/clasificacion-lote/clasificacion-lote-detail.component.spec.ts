import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { ClasificacionLoteDetailComponent } from 'app/entities/clasificacion-lote/clasificacion-lote-detail.component';
import { ClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

describe('Component Tests', () => {
  describe('ClasificacionLote Management Detail Component', () => {
    let comp: ClasificacionLoteDetailComponent;
    let fixture: ComponentFixture<ClasificacionLoteDetailComponent>;
    const route = ({ data: of({ clasificacionLote: new ClasificacionLote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [ClasificacionLoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClasificacionLoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClasificacionLoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load clasificacionLote on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clasificacionLote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
