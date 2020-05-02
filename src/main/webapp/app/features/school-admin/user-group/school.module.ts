import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';
import { SchoolDeleteDialogComponent } from './school-delete-dialog.component';
import { schoolRoute } from './school.route';
import { UserGroupComponent } from './user-group.component';
import { UserGroupDetailComponent } from './user-group-detail.component';
import { UserGroupUpdateComponent } from './user-group-update.component';
import { UserGroupDeleteDialogComponent } from './user-group-delete-dialog.component';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(schoolRoute)],
  declarations: [
    SchoolComponent,
    SchoolDetailComponent,
    SchoolUpdateComponent,
    SchoolDeleteDialogComponent,
    UserGroupComponent,
    UserGroupDetailComponent,
    UserGroupUpdateComponent,
    UserGroupDeleteDialogComponent
  ],
  entryComponents: [SchoolDeleteDialogComponent, UserGroupDeleteDialogComponent, UserGroupDetailComponent, UserGroupUpdateComponent]
})
export class HahuSchoolModule {}
