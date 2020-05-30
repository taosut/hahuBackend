import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { UserGroupDetailComponent } from './user-group-detail.component';
import { UserGroupUpdateComponent } from './user-group-update.component';
import { userGroupRoute } from './user-group.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(userGroupRoute)],
  declarations: [UserGroupDetailComponent, UserGroupUpdateComponent],
  entryComponents: [],
})
export class HahuUserGroupManageModule {}
