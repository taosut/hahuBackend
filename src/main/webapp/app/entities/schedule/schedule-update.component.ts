import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IScheduleType } from 'app/shared/model/schedule-type.model';
import { ScheduleTypeService } from 'app/entities/schedule-type/schedule-type.service';

type SelectableEntity = IUser | IUserGroup | IScheduleType;

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html',
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  usergroups: IUserGroup[] = [];
  scheduletypes: IScheduleType[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    startTime: [],
    endTime: [],
    userId: [],
    userGroupId: [],
    scheduleTypeId: [],
  });

  constructor(
    protected scheduleService: ScheduleService,
    protected userService: UserService,
    protected userGroupService: UserGroupService,
    protected scheduleTypeService: ScheduleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      if (!schedule.id) {
        const today = moment().startOf('day');
        schedule.startTime = today;
        schedule.endTime = today;
      }

      this.updateForm(schedule);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));

      this.scheduleTypeService.query().subscribe((res: HttpResponse<IScheduleType[]>) => (this.scheduletypes = res.body || []));
    });
  }

  updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      title: schedule.title,
      startTime: schedule.startTime ? schedule.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: schedule.endTime ? schedule.endTime.format(DATE_TIME_FORMAT) : null,
      userId: schedule.userId,
      userGroupId: schedule.userGroupId,
      scheduleTypeId: schedule.scheduleTypeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId'])!.value,
      userGroupId: this.editForm.get(['userGroupId'])!.value,
      scheduleTypeId: this.editForm.get(['scheduleTypeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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
