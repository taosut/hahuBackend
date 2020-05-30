import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { UserGroupDetailComponent } from './user-group-detail.component';
import { userGroupRoute } from './user-group.route';
import { MemberListComponent } from './member-list.component';
import { OwnerListComponent } from './owner-list.component';
import {UserManagementComponent} from "./user-management.component";

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(userGroupRoute)],
  declarations: [UserGroupDetailComponent, MemberListComponent, OwnerListComponent, UserManagementComponent],
  entryComponents: [UserManagementComponent],
})
export class HahuUserGroupManageModule {}
