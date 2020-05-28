import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IViews, Views } from 'app/shared/model/views.model';
import { ViewsService } from './views.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';

type SelectableEntity = IUser | IPost;

@Component({
  selector: 'jhi-views-update',
  templateUrl: './views-update.component.html',
})
export class ViewsUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  posts: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    registeredTime: [],
    userId: [],
    postId: [],
  });

  constructor(
    protected viewsService: ViewsService,
    protected userService: UserService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ views }) => {
      if (!views.id) {
        const today = moment().startOf('day');
        views.registeredTime = today;
      }

      this.updateForm(views);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
    });
  }

  updateForm(views: IViews): void {
    this.editForm.patchValue({
      id: views.id,
      registeredTime: views.registeredTime ? views.registeredTime.format(DATE_TIME_FORMAT) : null,
      userId: views.userId,
      postId: views.postId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const views = this.createFromForm();
    if (views.id !== undefined) {
      this.subscribeToSaveResponse(this.viewsService.update(views));
    } else {
      this.subscribeToSaveResponse(this.viewsService.create(views));
    }
  }

  private createFromForm(): IViews {
    return {
      ...new Views(),
      id: this.editForm.get(['id'])!.value,
      registeredTime: this.editForm.get(['registeredTime'])!.value
        ? moment(this.editForm.get(['registeredTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IViews>>): void {
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
