import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdditionalUserInfo, AdditionalUserInfo } from 'app/shared/model/additional-user-info.model';
import { AdditionalUserInfoService } from './additional-user-info.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';

type SelectableEntity = IUser | IUserGroup;

@Component({
  selector: 'jhi-additional-user-info-update',
  templateUrl: './additional-user-info-update.component.html'
})
export class AdditionalUserInfoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  usergroups: IUserGroup[] = [];

  editForm = this.fb.group({
    id: [],
    phone: [null, [Validators.pattern('^\\+[0-9]{12}$')]],
    profilePicture: [],
    userId: [],
    userGroupId: []
  });

  constructor(
    protected additionalUserInfoService: AdditionalUserInfoService,
    protected userService: UserService,
    protected userGroupService: UserGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ additionalUserInfo }) => {
      this.updateForm(additionalUserInfo);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));
    });
  }

  updateForm(additionalUserInfo: IAdditionalUserInfo): void {
    this.editForm.patchValue({
      id: additionalUserInfo.id,
      phone: additionalUserInfo.phone,
      profilePicture: additionalUserInfo.profilePicture,
      userId: additionalUserInfo.userId,
      userGroupId: additionalUserInfo.userGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const additionalUserInfo = this.createFromForm();
    if (additionalUserInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.additionalUserInfoService.update(additionalUserInfo));
    } else {
      this.subscribeToSaveResponse(this.additionalUserInfoService.create(additionalUserInfo));
    }
  }

  private createFromForm(): IAdditionalUserInfo {
    return {
      ...new AdditionalUserInfo(),
      id: this.editForm.get(['id'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      profilePicture: this.editForm.get(['profilePicture'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      userGroupId: this.editForm.get(['userGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdditionalUserInfo>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
