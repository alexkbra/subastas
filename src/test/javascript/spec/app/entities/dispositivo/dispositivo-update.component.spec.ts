import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { DispositivoUpdateComponent } from 'app/entities/dispositivo/dispositivo-update.component';
import { DispositivoService } from 'app/entities/dispositivo/dispositivo.service';
import { Dispositivo } from 'app/shared/model/dispositivo.model';

describe('Component Tests', () => {
  describe('Dispositivo Management Update Component', () => {
    let comp: DispositivoUpdateComponent;
    let fixture: ComponentFixture<DispositivoUpdateComponent>;
    let service: DispositivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [DispositivoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DispositivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DispositivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DispositivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dispositivo(123);
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
        const entity = new Dispositivo();
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
