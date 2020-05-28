import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { SharesComponent } from './shares.component';
import { SharesDetailComponent } from './shares-detail.component';
import { SharesUpdateComponent } from './shares-update.component';
import { SharesDeleteDialogComponent } from './shares-delete-dialog.component';
import { sharesRoute } from './shares.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(sharesRoute)],
  declarations: [SharesComponent, SharesDetailComponent, SharesUpdateComponent, SharesDeleteDialogComponent],
  entryComponents: [SharesDeleteDialogComponent],
})
export class HahuSharesModule {}
