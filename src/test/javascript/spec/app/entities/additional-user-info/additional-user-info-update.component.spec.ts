import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { AdditionalUserInfoUpdateComponent } from 'app/entities/additional-user-info/additional-user-info-update.component';
import { AdditionalUserInfoService } from 'app/entities/additional-user-info/additional-user-info.service';
import { AdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

describe('Component Tests', () => {
  describe('AdditionalUserInfo Management Update Component', () => {
    let comp: AdditionalUserInfoUpdateComponent;
    let fixture: ComponentFixture<AdditionalUserInfoUpdateComponent>;
    let service: AdditionalUserInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [AdditionalUserInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdditionalUserInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdditionalUserInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdditionalUserInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdditionalUserInfo(123);
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
        const entity = new AdditionalUserInfo();
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
