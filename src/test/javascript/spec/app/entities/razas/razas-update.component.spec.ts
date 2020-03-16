import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { RazasUpdateComponent } from 'app/entities/razas/razas-update.component';
import { RazasService } from 'app/entities/razas/razas.service';
import { Razas } from 'app/shared/model/razas.model';

describe('Component Tests', () => {
  describe('Razas Management Update Component', () => {
    let comp: RazasUpdateComponent;
    let fixture: ComponentFixture<RazasUpdateComponent>;
    let service: RazasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [RazasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RazasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RazasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RazasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Razas(123);
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
        const entity = new Razas();
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
