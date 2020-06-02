import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPreference, Preference } from 'app/shared/model/preference.model';
import { PreferenceService } from './preference.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

type SelectableEntity = IUser | ICategory;

@Component({
  selector: 'jhi-preference-update',
  templateUrl: './preference-update.component.html',
})
export class PreferenceUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    hasPrefereceSelected: [],
    userId: [],
    categories: [],
  });

  constructor(
    protected preferenceService: PreferenceService,
    protected userService: UserService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preference }) => {
      this.updateForm(preference);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(preference: IPreference): void {
    this.editForm.patchValue({
      id: preference.id,
      hasPrefereceSelected: preference.hasPrefereceSelected,
      userId: preference.userId,
      categories: preference.categories,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const preference = this.createFromForm();
    if (preference.id !== undefined) {
      this.subscribeToSaveResponse(this.preferenceService.update(preference));
    } else {
      this.subscribeToSaveResponse(this.preferenceService.create(preference));
    }
  }

  private createFromForm(): IPreference {
    return {
      ...new Preference(),
      id: this.editForm.get(['id'])!.value,
      hasPrefereceSelected: this.editForm.get(['hasPrefereceSelected'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      categories: this.editForm.get(['categories'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreference>>): void {
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

  getSelected(selectedVals: ICategory[], option: ICategory): ICategory {
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
