import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SubastasTestModule } from '../../../test.module';
import { PujadoresUpdateComponent } from 'app/entities/pujadores/pujadores-update.component';
import { PujadoresService } from 'app/entities/pujadores/pujadores.service';
import { Pujadores } from 'app/shared/model/pujadores.model';

describe('Component Tests', () => {
  describe('Pujadores Management Update Component', () => {
    let comp: PujadoresUpdateComponent;
    let fixture: ComponentFixture<PujadoresUpdateComponent>;
    let service: PujadoresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [PujadoresUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PujadoresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PujadoresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PujadoresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pujadores(123);
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
        const entity = new Pujadores();
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
