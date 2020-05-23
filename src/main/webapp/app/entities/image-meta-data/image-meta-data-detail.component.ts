import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImageMetaData } from 'app/shared/model/image-meta-data.model';

@Component({
  selector: 'jhi-image-meta-data-detail',
  templateUrl: './image-meta-data-detail.component.html',
})
export class ImageMetaDataDetailComponent implements OnInit {
  imageMetaData: IImageMetaData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imageMetaData }) => (this.imageMetaData = imageMetaData));
  }

  previousState(): void {
    window.history.back();
  }
}
