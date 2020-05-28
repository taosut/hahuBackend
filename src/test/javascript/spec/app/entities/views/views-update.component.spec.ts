import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ViewsUpdateComponent } from 'app/entities/views/views-update.component';
import { ViewsService } from 'app/entities/views/views.service';
import { Views } from 'app/shared/model/views.model';

describe('Component Tests', () => {
  describe('Views Management Update Component', () => {
    let comp: ViewsUpdateComponent;
    let fixture: ComponentFixture<ViewsUpdateComponent>;
    let service: ViewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ViewsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ViewsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ViewsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ViewsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Views(123);
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
        const entity = new Views();
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
