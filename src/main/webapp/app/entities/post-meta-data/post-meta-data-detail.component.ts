import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPostMetaData } from 'app/shared/model/post-meta-data.model';

@Component({
  selector: 'jhi-post-meta-data-detail',
  templateUrl: './post-meta-data-detail.component.html'
})
export class PostMetaDataDetailComponent implements OnInit {
  postMetaData: IPostMetaData | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postMetaData }) => (this.postMetaData = postMetaData));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
