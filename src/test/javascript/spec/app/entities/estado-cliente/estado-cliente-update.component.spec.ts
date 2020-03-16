import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { EstadoClienteUpdateComponent } from 'app/entities/estado-cliente/estado-cliente-update.component';
import { EstadoClienteService } from 'app/entities/estado-cliente/estado-cliente.service';
import { EstadoCliente } from 'app/shared/model/estado-cliente.model';

describe('Component Tests', () => {
  describe('EstadoCliente Management Update Component', () => {
    let comp: EstadoClienteUpdateComponent;
    let fixture: ComponentFixture<EstadoClienteUpdateComponent>;
    let service: EstadoClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [EstadoClienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoCliente(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoCliente();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
