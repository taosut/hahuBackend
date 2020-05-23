import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HahuSharedModule } from 'app/shared/shared.module';
import { ImageMetaDataComponent } from './image-meta-data.component';
import { ImageMetaDataDetailComponent } from './image-meta-data-detail.component';
import { ImageMetaDataUpdateComponent } from './image-meta-data-update.component';
import { ImageMetaDataDeleteDialogComponent } from './image-meta-data-delete-dialog.component';
import { imageMetaDataRoute } from './image-meta-data.route';

@NgModule({
  imports: [HahuSharedModule, RouterModule.forChild(imageMetaDataRoute)],
  declarations: [ImageMetaDataComponent, ImageMetaDataDetailComponent, ImageMetaDataUpdateComponent, ImageMetaDataDeleteDialogComponent],
  entryComponents: [ImageMetaDataDeleteDialogComponent],
})
export class HahuImageMetaDataModule {}
