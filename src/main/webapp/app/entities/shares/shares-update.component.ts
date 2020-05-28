import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IShares, Shares } from 'app/shared/model/shares.model';
import { SharesService } from './shares.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';

type SelectableEntity = IUser | IPost;

@Component({
  selector: 'jhi-shares-update',
  templateUrl: './shares-update.component.html',
})
export class SharesUpdateComponent implements OnInit {
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
    protected sharesService: SharesService,
    protected userService: UserService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shares }) => {
      if (!shares.id) {
        const today = moment().startOf('day');
        shares.registeredTime = today;
      }

      this.updateForm(shares);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
    });
  }

  updateForm(shares: IShares): void {
    this.editForm.patchValue({
      id: shares.id,
      registeredTime: shares.registeredTime ? shares.registeredTime.format(DATE_TIME_FORMAT) : null,
      userId: shares.userId,
      postId: shares.postId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shares = this.createFromForm();
    if (shares.id !== undefined) {
      this.subscribeToSaveResponse(this.sharesService.update(shares));
    } else {
      this.subscribeToSaveResponse(this.sharesService.create(shares));
    }
  }

  private createFromForm(): IShares {
    return {
      ...new Shares(),
      id: this.editForm.get(['id'])!.value,
      registeredTime: this.editForm.get(['registeredTime'])!.value
        ? moment(this.editForm.get(['registeredTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShares>>): void {
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
