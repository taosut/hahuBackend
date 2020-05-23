import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IImageMetaData, ImageMetaData } from 'app/shared/model/image-meta-data.model';
import { ImageMetaDataService } from './image-meta-data.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';

@Component({
  selector: 'jhi-image-meta-data-update',
  templateUrl: './image-meta-data-update.component.html',
})
export class ImageMetaDataUpdateComponent implements OnInit {
  isSaving = false;
  images: IImage[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
    imageId: [],
  });

  constructor(
    protected imageMetaDataService: ImageMetaDataService,
    protected imageService: ImageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imageMetaData }) => {
      this.updateForm(imageMetaData);

      this.imageService.query().subscribe((res: HttpResponse<IImage[]>) => (this.images = res.body || []));
    });
  }

  updateForm(imageMetaData: IImageMetaData): void {
    this.editForm.patchValue({
      id: imageMetaData.id,
      name: imageMetaData.name,
      value: imageMetaData.value,
      imageId: imageMetaData.imageId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const imageMetaData = this.createFromForm();
    if (imageMetaData.id !== undefined) {
      this.subscribeToSaveResponse(this.imageMetaDataService.update(imageMetaData));
    } else {
      this.subscribeToSaveResponse(this.imageMetaDataService.create(imageMetaData));
    }
  }

  private createFromForm(): IImageMetaData {
    return {
      ...new ImageMetaData(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      imageId: this.editForm.get(['imageId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImageMetaData>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IImage): any {
    return item.id;
  }
}
