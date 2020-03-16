import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { PujasDetailComponent } from 'app/entities/pujas/pujas-detail.component';
import { Pujas } from 'app/shared/model/pujas.model';

describe('Component Tests', () => {
  describe('Pujas Management Detail Component', () => {
    let comp: PujasDetailComponent;
    let fixture: ComponentFixture<PujasDetailComponent>;
    const route = ({ data: of({ pujas: new Pujas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [PujasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PujasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PujasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pujas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pujas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
