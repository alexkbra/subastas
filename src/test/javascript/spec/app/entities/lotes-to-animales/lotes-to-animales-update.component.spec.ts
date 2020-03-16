import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { LotesToAnimalesUpdateComponent } from 'app/entities/lotes-to-animales/lotes-to-animales-update.component';
import { LotesToAnimalesService } from 'app/entities/lotes-to-animales/lotes-to-animales.service';
import { LotesToAnimales } from 'app/shared/model/lotes-to-animales.model';

describe('Component Tests', () => {
  describe('LotesToAnimales Management Update Component', () => {
    let comp: LotesToAnimalesUpdateComponent;
    let fixture: ComponentFixture<LotesToAnimalesUpdateComponent>;
    let service: LotesToAnimalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [LotesToAnimalesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LotesToAnimalesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LotesToAnimalesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LotesToAnimalesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LotesToAnimales(123);
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
        const entity = new LotesToAnimales();
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
