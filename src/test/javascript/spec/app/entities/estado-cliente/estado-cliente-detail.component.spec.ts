import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { EstadoClienteDetailComponent } from 'app/entities/estado-cliente/estado-cliente-detail.component';
import { EstadoCliente } from 'app/shared/model/estado-cliente.model';

describe('Component Tests', () => {
  describe('EstadoCliente Management Detail Component', () => {
    let comp: EstadoClienteDetailComponent;
    let fixture: ComponentFixture<EstadoClienteDetailComponent>;
    const route = ({ data: of({ estadoCliente: new EstadoCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [EstadoClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoCliente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
