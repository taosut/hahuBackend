import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AdditionalUserInfoService } from './additional-user-info.service';
import { AdditionalUserInfoDeleteDialogComponent } from './additional-user-info-delete-dialog.component';

@Component({
  selector: 'jhi-additional-user-info',
  templateUrl: './additional-user-info.component.html'
})
export class AdditionalUserInfoComponent implements OnInit, OnDestroy {
  additionalUserInfos?: IAdditionalUserInfo[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected additionalUserInfoService: AdditionalUserInfoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.additionalUserInfoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAdditionalUserInfo[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAdditionalUserInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdditionalUserInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdditionalUserInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('additionalUserInfoListModification', () => this.loadPage());
  }

  delete(additionalUserInfo: IAdditionalUserInfo): void {
    const modalRef = this.modalService.open(AdditionalUserInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.additionalUserInfo = additionalUserInfo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAdditionalUserInfo[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/additional-user-info'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.additionalUserInfos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
