import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { EventosUpdateComponent } from 'app/entities/eventos/eventos-update.component';
import { EventosService } from 'app/entities/eventos/eventos.service';
import { Eventos } from 'app/shared/model/eventos.model';

describe('Component Tests', () => {
  describe('Eventos Management Update Component', () => {
    let comp: EventosUpdateComponent;
    let fixture: ComponentFixture<EventosUpdateComponent>;
    let service: EventosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [EventosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Eventos(123);
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
        const entity = new Eventos();
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
