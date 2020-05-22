import { Component, OnInit } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';

import { IUserGroup } from 'app/shared/model/user-group.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-group-owner-detail',
  templateUrl: './group-owner.component.html'
})
export class GroupOwnerComponent implements OnInit {
  userGroup: IUserGroup | null = null;

  constructor(protected dataUtils: JhiDataUtils, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  ngOnInit(): void {}

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
