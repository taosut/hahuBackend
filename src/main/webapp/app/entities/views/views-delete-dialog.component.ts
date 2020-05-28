import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IViews } from 'app/shared/model/views.model';
import { ViewsService } from './views.service';

@Component({
  templateUrl: './views-delete-dialog.component.html',
})
export class ViewsDeleteDialogComponent {
  views?: IViews;

  constructor(protected viewsService: ViewsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.viewsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('viewsListModification');
      this.activeModal.close();
    });
  }
}
