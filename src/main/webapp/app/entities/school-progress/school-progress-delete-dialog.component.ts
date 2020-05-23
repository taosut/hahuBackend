import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolProgress } from 'app/shared/model/school-progress.model';
import { SchoolProgressService } from './school-progress.service';

@Component({
  templateUrl: './school-progress-delete-dialog.component.html',
})
export class SchoolProgressDeleteDialogComponent {
  schoolProgress?: ISchoolProgress;

  constructor(
    protected schoolProgressService: SchoolProgressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolProgressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schoolProgressListModification');
      this.activeModal.close();
    });
  }
}
