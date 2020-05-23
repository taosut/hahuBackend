import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { UsersConnectionUpdateComponent } from 'app/entities/users-connection/users-connection-update.component';
import { UsersConnectionService } from 'app/entities/users-connection/users-connection.service';
import { UsersConnection } from 'app/shared/model/users-connection.model';

describe('Component Tests', () => {
  describe('UsersConnection Management Update Component', () => {
    let comp: UsersConnectionUpdateComponent;
    let fixture: ComponentFixture<UsersConnectionUpdateComponent>;
    let service: UsersConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [UsersConnectionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UsersConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsersConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsersConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsersConnection(123);
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
        const entity = new UsersConnection();
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
