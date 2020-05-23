import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImageMetaData } from 'app/shared/model/image-meta-data.model';
import { ImageMetaDataService } from './image-meta-data.service';

@Component({
  templateUrl: './image-meta-data-delete-dialog.component.html'
})
export class ImageMetaDataDeleteDialogComponent {
  imageMetaData?: IImageMetaData;

  constructor(
    protected imageMetaDataService: ImageMetaDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.imageMetaDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('imageMetaDataListModification');
      this.activeModal.close();
    });
  }
}
