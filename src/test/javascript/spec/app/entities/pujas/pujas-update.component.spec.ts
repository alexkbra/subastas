import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { PujasUpdateComponent } from 'app/entities/pujas/pujas-update.component';
import { PujasService } from 'app/entities/pujas/pujas.service';
import { Pujas } from 'app/shared/model/pujas.model';

describe('Component Tests', () => {
  describe('Pujas Management Update Component', () => {
    let comp: PujasUpdateComponent;
    let fixture: ComponentFixture<PujasUpdateComponent>;
    let service: PujasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [PujasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PujasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PujasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PujasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pujas(123);
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
        const entity = new Pujas();
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
