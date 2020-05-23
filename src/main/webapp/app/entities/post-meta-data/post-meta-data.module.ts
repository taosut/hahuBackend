import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { PostMetaDataComponent } from './post-meta-data.component';
import { PostMetaDataDetailComponent } from './post-meta-data-detail.component';
import { PostMetaDataUpdateComponent } from './post-meta-data-update.component';
import { PostMetaDataDeleteDialogComponent } from './post-meta-data-delete-dialog.component';
import { postMetaDataRoute } from './post-meta-data.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(postMetaDataRoute)],
  declarations: [PostMetaDataComponent, PostMetaDataDetailComponent, PostMetaDataUpdateComponent, PostMetaDataDeleteDialogComponent],
  entryComponents: [PostMetaDataDeleteDialogComponent],
})
export class HahuPostMetaDataModule {}
