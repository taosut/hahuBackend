import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheduleType } from 'app/shared/model/schedule-type.model';
import { ScheduleTypeService } from './schedule-type.service';

@Component({
  templateUrl: './schedule-type-delete-dialog.component.html'
})
export class ScheduleTypeDeleteDialogComponent {
  scheduleType?: IScheduleType;

  constructor(
    protected scheduleTypeService: ScheduleTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scheduleTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scheduleTypeListModification');
      this.activeModal.close();
    });
  }
}
