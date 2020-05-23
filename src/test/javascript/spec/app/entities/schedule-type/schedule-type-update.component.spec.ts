import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ScheduleTypeUpdateComponent } from 'app/entities/schedule-type/schedule-type-update.component';
import { ScheduleTypeService } from 'app/entities/schedule-type/schedule-type.service';
import { ScheduleType } from 'app/shared/model/schedule-type.model';

describe('Component Tests', () => {
  describe('ScheduleType Management Update Component', () => {
    let comp: ScheduleTypeUpdateComponent;
    let fixture: ComponentFixture<ScheduleTypeUpdateComponent>;
    let service: ScheduleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ScheduleTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ScheduleTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScheduleTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScheduleType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScheduleType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
