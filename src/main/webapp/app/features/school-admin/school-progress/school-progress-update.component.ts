import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchoolProgress, SchoolProgress } from 'app/shared/model/school-progress.model';
import { SchoolProgressService } from './school-progress.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-school-progress-update',
  templateUrl: './school-progress-update.component.html'
})
export class SchoolProgressUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    subject: [],
    year: [],
    semester: [],
    result: [],
    userId: []
  });

  constructor(
    protected schoolProgressService: SchoolProgressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolProgress }) => {
      this.updateForm(schoolProgress);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(schoolProgress: ISchoolProgress): void {
    this.editForm.patchValue({
      id: schoolProgress.id,
      subject: schoolProgress.subject,
      year: schoolProgress.year,
      semester: schoolProgress.semester,
      result: schoolProgress.result,
      userId: schoolProgress.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schoolProgress = this.createFromForm();
    if (schoolProgress.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolProgressService.update(schoolProgress));
    } else {
      this.subscribeToSaveResponse(this.schoolProgressService.create(schoolProgress));
    }
  }

  private createFromForm(): ISchoolProgress {
    return {
      ...new SchoolProgress(),
      id: this.editForm.get(['id'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      year: this.editForm.get(['year'])!.value,
      semester: this.editForm.get(['semester'])!.value,
      result: this.editForm.get(['result'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolProgress>>): void {
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
