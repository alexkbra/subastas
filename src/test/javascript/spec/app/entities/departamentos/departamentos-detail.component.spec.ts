import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { DepartamentosDetailComponent } from 'app/entities/departamentos/departamentos-detail.component';
import { Departamentos } from 'app/shared/model/departamentos.model';

describe('Component Tests', () => {
  describe('Departamentos Management Detail Component', () => {
    let comp: DepartamentosDetailComponent;
    let fixture: ComponentFixture<DepartamentosDetailComponent>;
    const route = ({ data: of({ departamentos: new Departamentos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [DepartamentosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepartamentosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartamentosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load departamentos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departamentos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
