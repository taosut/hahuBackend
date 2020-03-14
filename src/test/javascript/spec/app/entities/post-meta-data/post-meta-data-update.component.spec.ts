import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { PostMetaDataUpdateComponent } from 'app/entities/post-meta-data/post-meta-data-update.component';
import { PostMetaDataService } from 'app/entities/post-meta-data/post-meta-data.service';
import { PostMetaData } from 'app/shared/model/post-meta-data.model';

describe('Component Tests', () => {
  describe('PostMetaData Management Update Component', () => {
    let comp: PostMetaDataUpdateComponent;
    let fixture: ComponentFixture<PostMetaDataUpdateComponent>;
    let service: PostMetaDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [PostMetaDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PostMetaDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostMetaDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostMetaDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostMetaData(123);
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
        const entity = new PostMetaData();
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
