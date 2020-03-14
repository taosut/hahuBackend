import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ImageMetaDataUpdateComponent } from 'app/entities/image-meta-data/image-meta-data-update.component';
import { ImageMetaDataService } from 'app/entities/image-meta-data/image-meta-data.service';
import { ImageMetaData } from 'app/shared/model/image-meta-data.model';

describe('Component Tests', () => {
  describe('ImageMetaData Management Update Component', () => {
    let comp: ImageMetaDataUpdateComponent;
    let fixture: ComponentFixture<ImageMetaDataUpdateComponent>;
    let service: ImageMetaDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ImageMetaDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImageMetaDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImageMetaDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImageMetaDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImageMetaData(123);
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
        const entity = new ImageMetaData();
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
