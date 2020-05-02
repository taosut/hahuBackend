import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';

@Component({
  templateUrl: './school-delete-dialog.component.html'
})
export class SchoolDeleteDialogComponent {
  school?: ISchool;

  constructor(protected schoolService: SchoolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schoolListModification');
      this.activeModal.close();
    });
  }
}
