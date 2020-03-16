import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SubastasTestModule } from '../../../test.module';
import { ContenidoDetailComponent } from 'app/entities/contenido/contenido-detail.component';
import { Contenido } from 'app/shared/model/contenido.model';

describe('Component Tests', () => {
  describe('Contenido Management Detail Component', () => {
    let comp: ContenidoDetailComponent;
    let fixture: ComponentFixture<ContenidoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ contenido: new Contenido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [ContenidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContenidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContenidoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load contenido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contenido).toEqual(jasmine.objectContaining({ id: 123 }));
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
