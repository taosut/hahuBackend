import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';
import { SchoolDeleteDialogComponent } from './school-delete-dialog.component';
import { schoolRoute } from './school.route';
import { UserManagementComponent } from 'app/features/school/user-management.component';
import { UserManagementUpdateComponent } from 'app/features/school/user-management-update.component';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(schoolRoute)],
  declarations: [
    SchoolComponent,
    SchoolDetailComponent,
    SchoolUpdateComponent,
    SchoolDeleteDialogComponent,
    UserManagementComponent,
    UserManagementUpdateComponent
  ],
  entryComponents: [SchoolDeleteDialogComponent, UserManagementComponent, UserManagementUpdateComponent]
})
export class HahuSchoolModule {}
