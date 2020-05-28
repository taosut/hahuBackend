import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { ViewsComponent } from './views.component';
import { ViewsDetailComponent } from './views-detail.component';
import { ViewsUpdateComponent } from './views-update.component';
import { ViewsDeleteDialogComponent } from './views-delete-dialog.component';
import { viewsRoute } from './views.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(viewsRoute)],
  declarations: [ViewsComponent, ViewsDetailComponent, ViewsUpdateComponent, ViewsDeleteDialogComponent],
  entryComponents: [ViewsDeleteDialogComponent],
})
export class HahuViewsModule {}
