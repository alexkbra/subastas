import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { SubastasUpdateComponent } from 'app/entities/subastas/subastas-update.component';
import { SubastasService } from 'app/entities/subastas/subastas.service';
import { Subastas } from 'app/shared/model/subastas.model';

describe('Component Tests', () => {
  describe('Subastas Management Update Component', () => {
    let comp: SubastasUpdateComponent;
    let fixture: ComponentFixture<SubastasUpdateComponent>;
    let service: SubastasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [SubastasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubastasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubastasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubastasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Subastas(123);
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
        const entity = new Subastas();
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
