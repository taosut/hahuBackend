import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScheduleType, ScheduleType } from 'app/shared/model/schedule-type.model';
import { ScheduleTypeService } from './schedule-type.service';

@Component({
  selector: 'jhi-schedule-type-update',
  templateUrl: './schedule-type-update.component.html',
})
export class ScheduleTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected scheduleTypeService: ScheduleTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleType }) => {
      this.updateForm(scheduleType);
    });
  }

  updateForm(scheduleType: IScheduleType): void {
    this.editForm.patchValue({
      id: scheduleType.id,
      name: scheduleType.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheduleType = this.createFromForm();
    if (scheduleType.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleTypeService.update(scheduleType));
    } else {
      this.subscribeToSaveResponse(this.scheduleTypeService.create(scheduleType));
    }
  }

  private createFromForm(): IScheduleType {
    return {
      ...new ScheduleType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleType>>): void {
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
}
