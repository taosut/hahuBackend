import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsersConnection } from 'app/shared/model/users-connection.model';
import { UsersConnectionService } from './users-connection.service';

@Component({
  templateUrl: './users-connection-delete-dialog.component.html',
})
export class UsersConnectionDeleteDialogComponent {
  usersConnection?: IUsersConnection;

  constructor(
    protected usersConnectionService: UsersConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usersConnectionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usersConnectionListModification');
      this.activeModal.close();
    });
  }
}
