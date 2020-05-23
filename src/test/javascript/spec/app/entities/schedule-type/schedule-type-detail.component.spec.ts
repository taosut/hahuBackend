import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ScheduleTypeDetailComponent } from 'app/entities/schedule-type/schedule-type-detail.component';
import { ScheduleType } from 'app/shared/model/schedule-type.model';

describe('Component Tests', () => {
  describe('ScheduleType Management Detail Component', () => {
    let comp: ScheduleTypeDetailComponent;
    let fixture: ComponentFixture<ScheduleTypeDetailComponent>;
    const route = ({ data: of({ scheduleType: new ScheduleType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ScheduleTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScheduleTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScheduleTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scheduleType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scheduleType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
