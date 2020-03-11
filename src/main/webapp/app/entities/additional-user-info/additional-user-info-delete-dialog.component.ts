import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdditionalUserInfo } from 'app/shared/model/additional-user-info.model';
import { AdditionalUserInfoService } from './additional-user-info.service';

@Component({
  templateUrl: './additional-user-info-delete-dialog.component.html'
})
export class AdditionalUserInfoDeleteDialogComponent {
  additionalUserInfo?: IAdditionalUserInfo;

  constructor(
    protected additionalUserInfoService: AdditionalUserInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.additionalUserInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('additionalUserInfoListModification');
      this.activeModal.close();
    });
  }
}
