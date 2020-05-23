import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserGroup, UserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from './user-group.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school/school.service';

type SelectableEntity = IUser | ISchool;

@Component({
  selector: 'jhi-user-group-update',
  templateUrl: './user-group-update.component.html',
})
export class UserGroupUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  schools: ISchool[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    detail: [],
    profilePic: [],
    profilePicContentType: [],
    groupType: [],
    users: [],
    owners: [],
    schoolId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userGroupService: UserGroupService,
    protected userService: UserService,
    protected schoolService: SchoolService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroup }) => {
      this.updateForm(userGroup);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.schoolService.query().subscribe((res: HttpResponse<ISchool[]>) => (this.schools = res.body || []));
    });
  }

  updateForm(userGroup: IUserGroup): void {
    this.editForm.patchValue({
      id: userGroup.id,
      name: userGroup.name,
      detail: userGroup.detail,
      profilePic: userGroup.profilePic,
      profilePicContentType: userGroup.profilePicContentType,
      groupType: userGroup.groupType,
      users: userGroup.users,
      owners: userGroup.owners,
      schoolId: userGroup.schoolId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hahuApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userGroup = this.createFromForm();
    if (userGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupService.update(userGroup));
    } else {
      this.subscribeToSaveResponse(this.userGroupService.create(userGroup));
    }
  }

  private createFromForm(): IUserGroup {
    return {
      ...new UserGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      profilePicContentType: this.editForm.get(['profilePicContentType'])!.value,
      profilePic: this.editForm.get(['profilePic'])!.value,
      groupType: this.editForm.get(['groupType'])!.value,
      users: this.editForm.get(['users'])!.value,
      owners: this.editForm.get(['owners'])!.value,
      schoolId: this.editForm.get(['schoolId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroup>>): void {
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

  getSelected(selectedVals: IUser[], option: IUser): IUser {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
