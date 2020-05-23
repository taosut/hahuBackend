import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { UsersConnectionComponent } from './users-connection.component';
import { UsersConnectionDetailComponent } from './users-connection-detail.component';
import { UsersConnectionUpdateComponent } from './users-connection-update.component';
import { UsersConnectionDeleteDialogComponent } from './users-connection-delete-dialog.component';
import { usersConnectionRoute } from './users-connection.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(usersConnectionRoute)],
  declarations: [
    UsersConnectionComponent,
    UsersConnectionDetailComponent,
    UsersConnectionUpdateComponent,
    UsersConnectionDeleteDialogComponent,
  ],
  entryComponents: [UsersConnectionDeleteDialogComponent],
})
export class HahuUsersConnectionModule {}
