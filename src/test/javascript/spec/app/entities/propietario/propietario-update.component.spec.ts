import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { PropietarioUpdateComponent } from 'app/entities/propietario/propietario-update.component';
import { PropietarioService } from 'app/entities/propietario/propietario.service';
import { Propietario } from 'app/shared/model/propietario.model';

describe('Component Tests', () => {
  describe('Propietario Management Update Component', () => {
    let comp: PropietarioUpdateComponent;
    let fixture: ComponentFixture<PropietarioUpdateComponent>;
    let service: PropietarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [PropietarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PropietarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropietarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropietarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Propietario(123);
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
        const entity = new Propietario();
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
