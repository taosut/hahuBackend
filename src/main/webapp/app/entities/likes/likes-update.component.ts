import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILikes, Likes } from 'app/shared/model/likes.model';
import { LikesService } from './likes.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment/comment.service';

type SelectableEntity = IUser | IPost | IComment;

@Component({
  selector: 'jhi-likes-update',
  templateUrl: './likes-update.component.html',
})
export class LikesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  posts: IPost[] = [];
  comments: IComment[] = [];

  editForm = this.fb.group({
    id: [],
    registeredTime: [],
    userId: [],
    postId: [],
    commentId: [],
  });

  constructor(
    protected likesService: LikesService,
    protected userService: UserService,
    protected postService: PostService,
    protected commentService: CommentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likes }) => {
      if (!likes.id) {
        const today = moment().startOf('day');
        likes.registeredTime = today;
      }

      this.updateForm(likes);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));

      this.commentService.query().subscribe((res: HttpResponse<IComment[]>) => (this.comments = res.body || []));
    });
  }

  updateForm(likes: ILikes): void {
    this.editForm.patchValue({
      id: likes.id,
      registeredTime: likes.registeredTime ? likes.registeredTime.format(DATE_TIME_FORMAT) : null,
      userId: likes.userId,
      postId: likes.postId,
      commentId: likes.commentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const likes = this.createFromForm();
    if (likes.id !== undefined) {
      this.subscribeToSaveResponse(this.likesService.update(likes));
    } else {
      this.subscribeToSaveResponse(this.likesService.create(likes));
    }
  }

  private createFromForm(): ILikes {
    return {
      ...new Likes(),
      id: this.editForm.get(['id'])!.value,
      registeredTime: this.editForm.get(['registeredTime'])!.value
        ? moment(this.editForm.get(['registeredTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      commentId: this.editForm.get(['commentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILikes>>): void {
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
