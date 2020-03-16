import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { AnimalesUpdateComponent } from 'app/entities/animales/animales-update.component';
import { AnimalesService } from 'app/entities/animales/animales.service';
import { Animales } from 'app/shared/model/animales.model';

describe('Component Tests', () => {
  describe('Animales Management Update Component', () => {
    let comp: AnimalesUpdateComponent;
    let fixture: ComponentFixture<AnimalesUpdateComponent>;
    let service: AnimalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [AnimalesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Animales(123);
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
        const entity = new Animales();
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
