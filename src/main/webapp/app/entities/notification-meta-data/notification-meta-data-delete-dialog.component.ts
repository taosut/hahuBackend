import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificationMetaData } from 'app/shared/model/notification-meta-data.model';
import { NotificationMetaDataService } from './notification-meta-data.service';

@Component({
  templateUrl: './notification-meta-data-delete-dialog.component.html',
})
export class NotificationMetaDataDeleteDialogComponent {
  notificationMetaData?: INotificationMetaData;

  constructor(
    protected notificationMetaDataService: NotificationMetaDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationMetaDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificationMetaDataListModification');
      this.activeModal.close();
    });
  }
}
