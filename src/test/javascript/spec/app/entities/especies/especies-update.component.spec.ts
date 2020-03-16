import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { EspeciesUpdateComponent } from 'app/entities/especies/especies-update.component';
import { EspeciesService } from 'app/entities/especies/especies.service';
import { Especies } from 'app/shared/model/especies.model';

describe('Component Tests', () => {
  describe('Especies Management Update Component', () => {
    let comp: EspeciesUpdateComponent;
    let fixture: ComponentFixture<EspeciesUpdateComponent>;
    let service: EspeciesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [EspeciesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EspeciesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EspeciesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EspeciesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Especies(123);
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
        const entity = new Especies();
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
