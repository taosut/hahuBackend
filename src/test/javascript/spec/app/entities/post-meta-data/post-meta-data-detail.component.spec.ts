import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HahuTestModule } from '../../../test.module';
import { PostMetaDataDetailComponent } from 'app/entities/post-meta-data/post-meta-data-detail.component';
import { PostMetaData } from 'app/shared/model/post-meta-data.model';

describe('Component Tests', () => {
  describe('PostMetaData Management Detail Component', () => {
    let comp: PostMetaDataDetailComponent;
    let fixture: ComponentFixture<PostMetaDataDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ postMetaData: new PostMetaData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [PostMetaDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PostMetaDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostMetaDataDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load postMetaData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postMetaData).toEqual(jasmine.objectContaining({ id: 123 }));
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
