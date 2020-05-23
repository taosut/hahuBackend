import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPostMetaData, PostMetaData } from 'app/shared/model/post-meta-data.model';
import { PostMetaDataService } from './post-meta-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';

@Component({
  selector: 'jhi-post-meta-data-update',
  templateUrl: './post-meta-data-update.component.html',
})
export class PostMetaDataUpdateComponent implements OnInit {
  isSaving = false;
  posts: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
    blobValue: [],
    blobValueContentType: [],
    postId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected postMetaDataService: PostMetaDataService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postMetaData }) => {
      this.updateForm(postMetaData);

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
    });
  }

  updateForm(postMetaData: IPostMetaData): void {
    this.editForm.patchValue({
      id: postMetaData.id,
      name: postMetaData.name,
      value: postMetaData.value,
      blobValue: postMetaData.blobValue,
      blobValueContentType: postMetaData.blobValueContentType,
      postId: postMetaData.postId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hahuApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postMetaData = this.createFromForm();
    if (postMetaData.id !== undefined) {
      this.subscribeToSaveResponse(this.postMetaDataService.update(postMetaData));
    } else {
      this.subscribeToSaveResponse(this.postMetaDataService.create(postMetaData));
    }
  }

  private createFromForm(): IPostMetaData {
    return {
      ...new PostMetaData(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      blobValueContentType: this.editForm.get(['blobValueContentType'])!.value,
      blobValue: this.editForm.get(['blobValue'])!.value,
      postId: this.editForm.get(['postId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostMetaData>>): void {
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

  trackById(index: number, item: IPost): any {
    return item.id;
  }
}
