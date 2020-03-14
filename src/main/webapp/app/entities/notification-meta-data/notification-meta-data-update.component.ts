import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INotificationMetaData, NotificationMetaData } from 'app/shared/model/notification-meta-data.model';
import { NotificationMetaDataService } from './notification-meta-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification/notification.service';

@Component({
  selector: 'jhi-notification-meta-data-update',
  templateUrl: './notification-meta-data-update.component.html'
})
export class NotificationMetaDataUpdateComponent implements OnInit {
  isSaving = false;
  notifications: INotification[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
    blobValue: [],
    blobValueContentType: [],
    notificationId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected notificationMetaDataService: NotificationMetaDataService,
    protected notificationService: NotificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationMetaData }) => {
      this.updateForm(notificationMetaData);

      this.notificationService.query().subscribe((res: HttpResponse<INotification[]>) => (this.notifications = res.body || []));
    });
  }

  updateForm(notificationMetaData: INotificationMetaData): void {
    this.editForm.patchValue({
      id: notificationMetaData.id,
      name: notificationMetaData.name,
      value: notificationMetaData.value,
      blobValue: notificationMetaData.blobValue,
      blobValueContentType: notificationMetaData.blobValueContentType,
      notificationId: notificationMetaData.notificationId
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
    const notificationMetaData = this.createFromForm();
    if (notificationMetaData.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationMetaDataService.update(notificationMetaData));
    } else {
      this.subscribeToSaveResponse(this.notificationMetaDataService.create(notificationMetaData));
    }
  }

  private createFromForm(): INotificationMetaData {
    return {
      ...new NotificationMetaData(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      blobValueContentType: this.editForm.get(['blobValueContentType'])!.value,
      blobValue: this.editForm.get(['blobValue'])!.value,
      notificationId: this.editForm.get(['notificationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificationMetaData>>): void {
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

  trackById(index: number, item: INotification): any {
    return item.id;
  }
}
