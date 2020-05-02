import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { IUser, User } from 'app/core/user/user.model';
import { SchoolService } from './school.service';
import { ISchool } from 'app/shared/model/school.model';
import { Authority } from 'app/shared/constants/authority.constants';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html'
})
export class UserManagementComponent implements OnInit, OnDestroy {
  school!: ISchool | null;
  currentAccount: Account | null = null;
  users: User[] | null = null;
  selectedUsers: User[] = [];
  userListSubscription?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  previousPage!: number;
  ascending!: boolean;
  isSaving = false;

  constructor(
    private schoolService: SchoolService,
    public activeModal: NgbActiveModal,
    private userService: UserService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.ascending = true;
    this.predicate = 'id';
    this.loadAll();
    this.userListSubscription = this.eventManager.subscribe('userListModification', () => this.loadAll());
  }
  isThere(user: IUser): boolean {
    for (const item of this.selectedUsers) {
      if (item.id === user.id) {
        return true;
      }
    }
    return false;
  }
  isThereFromSchool(user: IUser): boolean {
    if (this.school && this.school.users) {
      for (const item of this.school.users) {
        if (item.id === user.id) {
          return true;
        }
      }
    }
    return false;
  }
  selectUser(user: IUser): void {
    if (this.isThere(user)) {
      this.selectedUsers.splice(this.selectedUsers.indexOf(user), 1);
    } else {
      this.selectedUsers.push(user);
    }
  }

  finish(): void {
    if (this.school) {
      this.selectedUsers.forEach(user => {
        if (user && this.school && this.school.users && !this.isThereFromSchool(user)) {
          if (user.authorities) {
            user.authorities.push(Authority.SCHOOL_ADMIN);
          }
          this.school.users.push(user);
          this.subscribeToSaveResponseUser(this.userService.update(user));
        }
      });
      this.subscribeToSaveResponse(this.schoolService.update(this.school));
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchool>>): void {
    result.subscribe(
      res => this.onSaveSuccess(res.body),
      () => this.onSaveError()
    );
  }
  protected subscribeToSaveResponseUser(result: Observable<IUser>): void {
    result.subscribe(
      () => {},
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(school: ISchool | null): void {
    this.isSaving = false;
    this.school = school;
    this.activeModal.dismiss();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
  ngOnDestroy(): void {
    if (this.userListSubscription) {
      this.eventManager.destroy(this.userListSubscription);
    }
  }

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  trackIdentity(index: number, item: User): any {
    return item.id;
  }

  loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition(): void {
    if (this.school) {
      this.router.navigate(['school-management', this.school.id, 'view'], {
        queryParams: {
          page: this.page,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
        }
      });
      this.loadAll();
    }
  }
  private loadAll(): void {
    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers));
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }
}
