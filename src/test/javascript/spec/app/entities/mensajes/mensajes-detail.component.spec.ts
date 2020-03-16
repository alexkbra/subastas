import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SubastasTestModule } from '../../../test.module';
import { MensajesDetailComponent } from 'app/entities/mensajes/mensajes-detail.component';
import { Mensajes } from 'app/shared/model/mensajes.model';

describe('Component Tests', () => {
  describe('Mensajes Management Detail Component', () => {
    let comp: MensajesDetailComponent;
    let fixture: ComponentFixture<MensajesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ mensajes: new Mensajes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [MensajesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MensajesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MensajesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load mensajes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mensajes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
