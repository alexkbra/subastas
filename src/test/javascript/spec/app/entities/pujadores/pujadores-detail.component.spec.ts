import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { PujadoresDetailComponent } from 'app/entities/pujadores/pujadores-detail.component';
import { Pujadores } from 'app/shared/model/pujadores.model';

describe('Component Tests', () => {
  describe('Pujadores Management Detail Component', () => {
    let comp: PujadoresDetailComponent;
    let fixture: ComponentFixture<PujadoresDetailComponent>;
    const route = ({ data: of({ pujadores: new Pujadores(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [PujadoresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PujadoresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PujadoresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pujadores on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pujadores).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
