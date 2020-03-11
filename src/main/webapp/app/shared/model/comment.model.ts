import { Moment } from 'moment';
import { ILikes } from 'app/shared/model/likes.model';

export interface IComment {
  id?: number;
  content?: any;
  postedDate?: Moment;
  modifiedDate?: Moment;
  replies?: IComment[];
  likes?: ILikes[];
  postId?: number;
  commentId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public content?: any,
    public postedDate?: Moment,
    public modifiedDate?: Moment,
    public replies?: IComment[],
    public likes?: ILikes[],
    public postId?: number,
    public commentId?: number
  ) {}
}
