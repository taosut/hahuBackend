import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HahuTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ImageMetaDataDeleteDialogComponent } from 'app/entities/image-meta-data/image-meta-data-delete-dialog.component';
import { ImageMetaDataService } from 'app/entities/image-meta-data/image-meta-data.service';

describe('Component Tests', () => {
  describe('ImageMetaData Management Delete Component', () => {
    let comp: ImageMetaDataDeleteDialogComponent;
    let fixture: ComponentFixture<ImageMetaDataDeleteDialogComponent>;
    let service: ImageMetaDataService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ImageMetaDataDeleteDialogComponent]
      })
        .overrideTemplate(ImageMetaDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImageMetaDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImageMetaDataService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
