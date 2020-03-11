import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { PreferenceComponent } from './preference.component';
import { PreferenceDetailComponent } from './preference-detail.component';
import { PreferenceUpdateComponent } from './preference-update.component';
import { PreferenceDeleteDialogComponent } from './preference-delete-dialog.component';
import { preferenceRoute } from './preference.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(preferenceRoute)],
  declarations: [PreferenceComponent, PreferenceDetailComponent, PreferenceUpdateComponent, PreferenceDeleteDialogComponent],
  entryComponents: [PreferenceDeleteDialogComponent]
})
export class HahuPreferenceModule {}
