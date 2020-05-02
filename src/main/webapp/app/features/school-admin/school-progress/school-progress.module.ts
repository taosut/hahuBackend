import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { SchoolProgressComponent } from './school-progress.component';
import { SchoolProgressDetailComponent } from './school-progress-detail.component';
import { SchoolProgressUpdateComponent } from './school-progress-update.component';
import { SchoolProgressDeleteDialogComponent } from './school-progress-delete-dialog.component';
import { schoolProgressRoute } from './school-progress.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(schoolProgressRoute)],
  declarations: [
    SchoolProgressComponent,
    SchoolProgressDetailComponent,
    SchoolProgressUpdateComponent,
    SchoolProgressDeleteDialogComponent
  ],
  entryComponents: [SchoolProgressDeleteDialogComponent]
})
export class HahuSchoolProgressModule {}
