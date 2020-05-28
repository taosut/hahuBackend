import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ViewsDetailComponent } from 'app/entities/views/views-detail.component';
import { Views } from 'app/shared/model/views.model';

describe('Component Tests', () => {
  describe('Views Management Detail Component', () => {
    let comp: ViewsDetailComponent;
    let fixture: ComponentFixture<ViewsDetailComponent>;
    const route = ({ data: of({ views: new Views(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ViewsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ViewsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ViewsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load views on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.views).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
