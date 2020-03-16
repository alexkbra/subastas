import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { DepartamentosUpdateComponent } from 'app/entities/departamentos/departamentos-update.component';
import { DepartamentosService } from 'app/entities/departamentos/departamentos.service';
import { Departamentos } from 'app/shared/model/departamentos.model';

describe('Component Tests', () => {
  describe('Departamentos Management Update Component', () => {
    let comp: DepartamentosUpdateComponent;
    let fixture: ComponentFixture<DepartamentosUpdateComponent>;
    let service: DepartamentosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [DepartamentosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepartamentosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartamentosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartamentosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Departamentos(123);
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
        const entity = new Departamentos();
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
