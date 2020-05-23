import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsersConnection } from 'app/shared/model/users-connection.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UsersConnectionService } from './users-connection.service';
import { UsersConnectionDeleteDialogComponent } from './users-connection-delete-dialog.component';

@Component({
  selector: 'jhi-users-connection',
  templateUrl: './users-connection.component.html'
})
export class UsersConnectionComponent implements OnInit, OnDestroy {
  usersConnections?: IUsersConnection[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected usersConnectionService: UsersConnectionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.usersConnectionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IUsersConnection[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInUsersConnections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsersConnection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsersConnections(): void {
    this.eventSubscriber = this.eventManager.subscribe('usersConnectionListModification', () => this.loadPage());
  }

  delete(usersConnection: IUsersConnection): void {
    const modalRef = this.modalService.open(UsersConnectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usersConnection = usersConnection;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUsersConnection[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/users-connection'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.usersConnections = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
