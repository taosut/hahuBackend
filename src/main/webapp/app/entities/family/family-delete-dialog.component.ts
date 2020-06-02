import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';

@Component({
  templateUrl: './family-delete-dialog.component.html',
})
export class FamilyDeleteDialogComponent {
  family?: IFamily;

  constructor(protected familyService: FamilyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.familyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('familyListModification');
      this.activeModal.close();
    });
  }
}
