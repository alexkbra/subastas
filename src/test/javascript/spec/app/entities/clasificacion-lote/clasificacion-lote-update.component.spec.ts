import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { ClasificacionLoteUpdateComponent } from 'app/entities/clasificacion-lote/clasificacion-lote-update.component';
import { ClasificacionLoteService } from 'app/entities/clasificacion-lote/clasificacion-lote.service';
import { ClasificacionLote } from 'app/shared/model/clasificacion-lote.model';

describe('Component Tests', () => {
  describe('ClasificacionLote Management Update Component', () => {
    let comp: ClasificacionLoteUpdateComponent;
    let fixture: ComponentFixture<ClasificacionLoteUpdateComponent>;
    let service: ClasificacionLoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [ClasificacionLoteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClasificacionLoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClasificacionLoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasificacionLoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClasificacionLote(123);
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
        const entity = new ClasificacionLote();
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
