import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HahuTestModule } from '../../../test.module';
import { NotificationMetaDataDetailComponent } from 'app/entities/notification-meta-data/notification-meta-data-detail.component';
import { NotificationMetaData } from 'app/shared/model/notification-meta-data.model';

describe('Component Tests', () => {
  describe('NotificationMetaData Management Detail Component', () => {
    let comp: NotificationMetaDataDetailComponent;
    let fixture: ComponentFixture<NotificationMetaDataDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ notificationMetaData: new NotificationMetaData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [NotificationMetaDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotificationMetaDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificationMetaDataDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load notificationMetaData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificationMetaData).toEqual(jasmine.objectContaining({ id: 123 }));
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
