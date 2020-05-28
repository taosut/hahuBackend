import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShares } from 'app/shared/model/shares.model';
import { SharesService } from './shares.service';

@Component({
  templateUrl: './shares-delete-dialog.component.html',
})
export class SharesDeleteDialogComponent {
  shares?: IShares;

  constructor(protected sharesService: SharesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sharesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sharesListModification');
      this.activeModal.close();
    });
  }
}
