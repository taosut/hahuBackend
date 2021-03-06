import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserGroup } from 'app/shared/model/user-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UserGroupService } from './user-group.service';
import { UserGroupDeleteDialogComponent } from './user-group-delete-dialog.component';
import { UserGroupDetailComponent } from './user-group-detail.component';
import { UserGroupUpdateComponent } from './user-group-update.component';
import { ISchool } from 'app/shared/model/school.model';

@Component({
  selector: 'jhi-school-user-group',
  templateUrl: './user-group.component.html'
})
export class UserGroupComponent implements OnInit, OnDestroy {
  userGroups?: IUserGroup[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  @Input() school!: ISchool | null;

  constructor(
    protected userGroupService: UserGroupService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    if (this.school) {
      this.userGroupService
        .query({
          'schoolId.equals': this.school.id,
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IUserGroup[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInUserGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInUserGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('userGroupListModification', () => this.loadPage());
  }

  delete(userGroup: IUserGroup): void {
    const modalRef = this.modalService.open(UserGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userGroup = userGroup;
  }

  detail(userGroup: IUserGroup): void {
    const modalRef = this.modalService.open(UserGroupDetailComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userGroup = userGroup;
  }

  update(userGroup?: IUserGroup): void {
    const modalRef = this.modalService.open(UserGroupUpdateComponent, { size: 'xl', backdrop: 'static' });
    if (userGroup) {
      modalRef.componentInstance.userGroup = userGroup;
    } else {
      modalRef.componentInstance.school = this.school;
    }
  }
  manage(userGroup: IUserGroup): void{
    this.router.navigate(['school-user-group-manage', userGroup.id , 'view']);
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUserGroup[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.userGroups = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
