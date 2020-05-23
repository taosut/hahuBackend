import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchoolProgress } from 'app/shared/model/school-progress.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SchoolProgressService } from './school-progress.service';
import { SchoolProgressDeleteDialogComponent } from './school-progress-delete-dialog.component';

@Component({
  selector: 'jhi-school-progress',
  templateUrl: './school-progress.component.html'
})
export class SchoolProgressComponent implements OnInit, OnDestroy {
  schoolProgresses?: ISchoolProgress[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected schoolProgressService: SchoolProgressService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.schoolProgressService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISchoolProgress[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSchoolProgresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISchoolProgress): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSchoolProgresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('schoolProgressListModification', () => this.loadPage());
  }

  delete(schoolProgress: ISchoolProgress): void {
    const modalRef = this.modalService.open(SchoolProgressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.schoolProgress = schoolProgress;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISchoolProgress[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/school-progress'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.schoolProgresses = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
