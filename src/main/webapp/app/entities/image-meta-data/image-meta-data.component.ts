import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IImageMetaData } from 'app/shared/model/image-meta-data.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ImageMetaDataService } from './image-meta-data.service';
import { ImageMetaDataDeleteDialogComponent } from './image-meta-data-delete-dialog.component';

@Component({
  selector: 'jhi-image-meta-data',
  templateUrl: './image-meta-data.component.html'
})
export class ImageMetaDataComponent implements OnInit, OnDestroy {
  imageMetaData?: IImageMetaData[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected imageMetaDataService: ImageMetaDataService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.imageMetaDataService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IImageMetaData[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInImageMetaData();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IImageMetaData): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInImageMetaData(): void {
    this.eventSubscriber = this.eventManager.subscribe('imageMetaDataListModification', () => this.loadPage());
  }

  delete(imageMetaData: IImageMetaData): void {
    const modalRef = this.modalService.open(ImageMetaDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.imageMetaData = imageMetaData;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IImageMetaData[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/image-meta-data'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.imageMetaData = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
