import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { LotesToAnimalesDetailComponent } from 'app/entities/lotes-to-animales/lotes-to-animales-detail.component';
import { LotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

describe('Component Tests', () => {
  describe('LotesToAnimales Management Detail Component', () => {
    let comp: LotesToAnimalesDetailComponent;
    let fixture: ComponentFixture<LotesToAnimalesDetailComponent>;
    const route = ({ data: of({ lotesToAnimales: new LotesToAnimales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [LotesToAnimalesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LotesToAnimalesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LotesToAnimalesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lotesToAnimales on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lotesToAnimales).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
