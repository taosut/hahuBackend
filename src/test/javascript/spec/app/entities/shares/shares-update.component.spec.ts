import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { SharesUpdateComponent } from 'app/entities/shares/shares-update.component';
import { SharesService } from 'app/entities/shares/shares.service';
import { Shares } from 'app/shared/model/shares.model';

describe('Component Tests', () => {
  describe('Shares Management Update Component', () => {
    let comp: SharesUpdateComponent;
    let fixture: ComponentFixture<SharesUpdateComponent>;
    let service: SharesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [SharesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SharesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SharesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SharesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Shares(123);
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
        const entity = new Shares();
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
