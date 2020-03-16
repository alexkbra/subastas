import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { DispositivoDetailComponent } from 'app/entities/dispositivo/dispositivo-detail.component';
import { Dispositivo } from 'app/shared/model/dispositivo.model';

describe('Component Tests', () => {
  describe('Dispositivo Management Detail Component', () => {
    let comp: DispositivoDetailComponent;
    let fixture: ComponentFixture<DispositivoDetailComponent>;
    const route = ({ data: of({ dispositivo: new Dispositivo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [DispositivoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DispositivoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DispositivoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dispositivo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dispositivo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
