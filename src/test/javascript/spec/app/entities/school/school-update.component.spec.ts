import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { SchoolUpdateComponent } from 'app/entities/school/school-update.component';
import { SchoolService } from 'app/entities/school/school.service';
import { School } from 'app/shared/model/school.model';

describe('Component Tests', () => {
  describe('School Management Update Component', () => {
    let comp: SchoolUpdateComponent;
    let fixture: ComponentFixture<SchoolUpdateComponent>;
    let service: SchoolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [SchoolUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SchoolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchoolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchoolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new School(123);
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
        const entity = new School();
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
