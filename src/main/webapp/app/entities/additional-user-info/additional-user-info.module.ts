import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { AdditionalUserInfoComponent } from './additional-user-info.component';
import { AdditionalUserInfoDetailComponent } from './additional-user-info-detail.component';
import { AdditionalUserInfoUpdateComponent } from './additional-user-info-update.component';
import { AdditionalUserInfoDeleteDialogComponent } from './additional-user-info-delete-dialog.component';
import { additionalUserInfoRoute } from './additional-user-info.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(additionalUserInfoRoute)],
  declarations: [
    AdditionalUserInfoComponent,
    AdditionalUserInfoDetailComponent,
    AdditionalUserInfoUpdateComponent,
    AdditionalUserInfoDeleteDialogComponent
  ],
  entryComponents: [AdditionalUserInfoDeleteDialogComponent]
})
export class HahuAdditionalUserInfoModule {}
