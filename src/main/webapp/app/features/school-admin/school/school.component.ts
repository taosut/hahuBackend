import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchool } from 'app/shared/model/school.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SchoolService } from './school.service';
import { SchoolDeleteDialogComponent } from './school-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-school',
  templateUrl: './school.component.html'
})
export class SchoolComponent implements OnInit, OnDestroy {
  schools?: ISchool[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  userId!: number;

  constructor(
    protected userService: UserService,
    protected accountService: AccountService,
    protected schoolService: SchoolService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.schoolService
      .query({
        'userId.equals': this.userId,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISchool[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => {
          if (user) {
            this.userId = user.id;
            this.activatedRoute.data.subscribe(data => {
              this.page = data.pagingParams.page;
              this.ascending = data.pagingParams.ascending;
              this.predicate = data.pagingParams.predicate;
              this.ngbPaginationPage = data.pagingParams.page;
              this.loadPage();
            });
          }
        });
      }
    });
    this.registerChangeInSchools();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISchool): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInSchools(): void {
    this.eventSubscriber = this.eventManager.subscribe('schoolListModification', () => this.loadPage());
  }

  delete(school: ISchool): void {
    const modalRef = this.modalService.open(SchoolDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.school = school;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISchool[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    // this.router.navigate(['/school'], {
    //   queryParams: {
    //     page: this.page,
    //     size: this.itemsPerPage,
    //     sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
    //   }
    // });
    this.schools = data || [];
    if (this.schools.length === 1) {
      this.router.navigate(['/school-user-management', this.schools[0].id, 'view', 1]);
    }
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
