import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INotificationMetaData } from 'app/shared/model/notification-meta-data.model';

@Component({
  selector: 'jhi-notification-meta-data-detail',
  templateUrl: './notification-meta-data-detail.component.html',
})
export class NotificationMetaDataDetailComponent implements OnInit {
  notificationMetaData: INotificationMetaData | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationMetaData }) => (this.notificationMetaData = notificationMetaData));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
