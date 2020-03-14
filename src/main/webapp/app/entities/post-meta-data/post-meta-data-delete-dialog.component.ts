import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostMetaData } from 'app/shared/model/post-meta-data.model';
import { PostMetaDataService } from './post-meta-data.service';

@Component({
  templateUrl: './post-meta-data-delete-dialog.component.html'
})
export class PostMetaDataDeleteDialogComponent {
  postMetaData?: IPostMetaData;

  constructor(
    protected postMetaDataService: PostMetaDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postMetaDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('postMetaDataListModification');
      this.activeModal.close();
    });
  }
}
