import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SubastasTestModule } from '../../../test.module';
import { EspeciesDetailComponent } from 'app/entities/especies/especies-detail.component';
import { Especies } from 'app/shared/model/especies.model';

describe('Component Tests', () => {
  describe('Especies Management Detail Component', () => {
    let comp: EspeciesDetailComponent;
    let fixture: ComponentFixture<EspeciesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ especies: new Especies(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SubastasTestModule],
        declarations: [EspeciesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EspeciesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EspeciesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load especies on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.especies).toEqual(jasmine.objectContaining({ id: 123 }));
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
