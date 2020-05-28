import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { SharesDetailComponent } from 'app/entities/shares/shares-detail.component';
import { Shares } from 'app/shared/model/shares.model';

describe('Component Tests', () => {
  describe('Shares Management Detail Component', () => {
    let comp: SharesDetailComponent;
    let fixture: ComponentFixture<SharesDetailComponent>;
    const route = ({ data: of({ shares: new Shares(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [SharesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SharesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SharesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shares on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shares).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
