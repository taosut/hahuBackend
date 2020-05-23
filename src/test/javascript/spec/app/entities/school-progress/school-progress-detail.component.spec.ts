import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { SchoolProgressDetailComponent } from 'app/entities/school-progress/school-progress-detail.component';
import { SchoolProgress } from 'app/shared/model/school-progress.model';

describe('Component Tests', () => {
  describe('SchoolProgress Management Detail Component', () => {
    let comp: SchoolProgressDetailComponent;
    let fixture: ComponentFixture<SchoolProgressDetailComponent>;
    const route = ({ data: of({ schoolProgress: new SchoolProgress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [SchoolProgressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SchoolProgressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchoolProgressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schoolProgress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schoolProgress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
