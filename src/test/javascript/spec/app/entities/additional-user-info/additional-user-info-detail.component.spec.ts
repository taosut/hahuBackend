import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { AdditionalUserInfoDetailComponent } from 'app/entities/additional-user-info/additional-user-info-detail.component';
import { AdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

describe('Component Tests', () => {
  describe('AdditionalUserInfo Management Detail Component', () => {
    let comp: AdditionalUserInfoDetailComponent;
    let fixture: ComponentFixture<AdditionalUserInfoDetailComponent>;
    const route = ({ data: of({ additionalUserInfo: new AdditionalUserInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [AdditionalUserInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdditionalUserInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdditionalUserInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load additionalUserInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.additionalUserInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
