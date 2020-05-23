import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { LikesUpdateComponent } from 'app/entities/likes/likes-update.component';
import { LikesService } from 'app/entities/likes/likes.service';
import { Likes } from 'app/shared/model/likes.model';

describe('Component Tests', () => {
  describe('Likes Management Update Component', () => {
    let comp: LikesUpdateComponent;
    let fixture: ComponentFixture<LikesUpdateComponent>;
    let service: LikesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [LikesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LikesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Likes(123);
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
        const entity = new Likes();
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
