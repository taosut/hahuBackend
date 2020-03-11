import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILikes } from 'app/shared/model/likes.model';
import { LikesService } from './likes.service';

@Component({
  templateUrl: './likes-delete-dialog.component.html'
})
export class LikesDeleteDialogComponent {
  likes?: ILikes;

  constructor(protected likesService: LikesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.likesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('likesListModification');
      this.activeModal.close();
    });
  }
}
