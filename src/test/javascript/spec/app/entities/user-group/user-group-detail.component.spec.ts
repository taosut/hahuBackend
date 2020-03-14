import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HahuTestModule } from '../../../test.module';
import { UserGroupDetailComponent } from 'app/entities/user-group/user-group-detail.component';
import { UserGroup } from 'app/shared/model/user-group.model';

describe('Component Tests', () => {
  describe('UserGroup Management Detail Component', () => {
    let comp: UserGroupDetailComponent;
    let fixture: ComponentFixture<UserGroupDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ userGroup: new UserGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [UserGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserGroupDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load userGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userGroup).toEqual(jasmine.objectContaining({ id: 123 }));
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
