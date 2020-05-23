import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsersConnection, UsersConnection } from 'app/shared/model/users-connection.model';
import { UsersConnectionService } from './users-connection.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-users-connection-update',
  templateUrl: './users-connection-update.component.html',
})
export class UsersConnectionUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    registeredTime: [],
    followerId: [],
    followingId: [],
  });

  constructor(
    protected usersConnectionService: UsersConnectionService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usersConnection }) => {
      if (!usersConnection.id) {
        const today = moment().startOf('day');
        usersConnection.registeredTime = today;
      }

      this.updateForm(usersConnection);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(usersConnection: IUsersConnection): void {
    this.editForm.patchValue({
      id: usersConnection.id,
      registeredTime: usersConnection.registeredTime ? usersConnection.registeredTime.format(DATE_TIME_FORMAT) : null,
      followerId: usersConnection.followerId,
      followingId: usersConnection.followingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usersConnection = this.createFromForm();
    if (usersConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.usersConnectionService.update(usersConnection));
    } else {
      this.subscribeToSaveResponse(this.usersConnectionService.create(usersConnection));
    }
  }

  private createFromForm(): IUsersConnection {
    return {
      ...new UsersConnection(),
      id: this.editForm.get(['id'])!.value,
      registeredTime: this.editForm.get(['registeredTime'])!.value
        ? moment(this.editForm.get(['registeredTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      followerId: this.editForm.get(['followerId'])!.value,
      followingId: this.editForm.get(['followingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsersConnection>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
