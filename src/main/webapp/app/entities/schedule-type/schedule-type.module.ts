import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { ScheduleTypeComponent } from './schedule-type.component';
import { ScheduleTypeDetailComponent } from './schedule-type-detail.component';
import { ScheduleTypeUpdateComponent } from './schedule-type-update.component';
import { ScheduleTypeDeleteDialogComponent } from './schedule-type-delete-dialog.component';
import { scheduleTypeRoute } from './schedule-type.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(scheduleTypeRoute)],
  declarations: [ScheduleTypeComponent, ScheduleTypeDetailComponent, ScheduleTypeUpdateComponent, ScheduleTypeDeleteDialogComponent],
  entryComponents: [ScheduleTypeDeleteDialogComponent]
})
export class HahuScheduleTypeModule {}
