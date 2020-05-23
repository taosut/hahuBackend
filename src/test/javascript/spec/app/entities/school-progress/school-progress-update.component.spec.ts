import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { SchoolProgressUpdateComponent } from 'app/entities/school-progress/school-progress-update.component';
import { SchoolProgressService } from 'app/entities/school-progress/school-progress.service';
import { SchoolProgress } from 'app/shared/model/school-progress.model';

describe('Component Tests', () => {
  describe('SchoolProgress Management Update Component', () => {
    let comp: SchoolProgressUpdateComponent;
    let fixture: ComponentFixture<SchoolProgressUpdateComponent>;
    let service: SchoolProgressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [SchoolProgressUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SchoolProgressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchoolProgressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchoolProgressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchoolProgress(123);
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
        const entity = new SchoolProgress();
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
