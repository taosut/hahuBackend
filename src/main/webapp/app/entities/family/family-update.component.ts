import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamily, Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html',
})
export class FamilyUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    hasFamilyControl: [],
    userId: [],
    parents: [],
  });

  constructor(
    protected familyService: FamilyService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.updateForm(family);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(family: IFamily): void {
    this.editForm.patchValue({
      id: family.id,
      hasFamilyControl: family.hasFamilyControl,
      userId: family.userId,
      parents: family.parents,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
  }

  private createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id'])!.value,
      hasFamilyControl: this.editForm.get(['hasFamilyControl'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      parents: this.editForm.get(['parents'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>): void {
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
