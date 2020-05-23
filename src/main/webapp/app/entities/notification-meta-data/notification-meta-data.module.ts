import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { NotificationMetaDataComponent } from './notification-meta-data.component';
import { NotificationMetaDataDetailComponent } from './notification-meta-data-detail.component';
import { NotificationMetaDataUpdateComponent } from './notification-meta-data-update.component';
import { NotificationMetaDataDeleteDialogComponent } from './notification-meta-data-delete-dialog.component';
import { notificationMetaDataRoute } from './notification-meta-data.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(notificationMetaDataRoute)],
  declarations: [
    NotificationMetaDataComponent,
    NotificationMetaDataDetailComponent,
    NotificationMetaDataUpdateComponent,
    NotificationMetaDataDeleteDialogComponent,
  ],
  entryComponents: [NotificationMetaDataDeleteDialogComponent],
})
export class HahuNotificationMetaDataModule {}
