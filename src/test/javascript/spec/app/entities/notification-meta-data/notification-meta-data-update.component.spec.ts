import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { NotificationMetaDataUpdateComponent } from 'app/entities/notification-meta-data/notification-meta-data-update.component';
import { NotificationMetaDataService } from 'app/entities/notification-meta-data/notification-meta-data.service';
import { NotificationMetaData } from 'app/shared/model/notification-meta-data.model';

describe('Component Tests', () => {
  describe('NotificationMetaData Management Update Component', () => {
    let comp: NotificationMetaDataUpdateComponent;
    let fixture: ComponentFixture<NotificationMetaDataUpdateComponent>;
    let service: NotificationMetaDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [NotificationMetaDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotificationMetaDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificationMetaDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificationMetaDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotificationMetaData(123);
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
        const entity = new NotificationMetaData();
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
