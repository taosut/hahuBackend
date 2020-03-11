import { Moment } from 'moment';

export interface ILikes {
  id?: number;
  registeredTime?: Moment;
  userLogin?: string;
  userId?: number;
  postId?: number;
  commentId?: number;
}

export class Likes implements ILikes {
  constructor(
    public id?: number,
    public registeredTime?: Moment,
    public userLogin?: string,
    public userId?: number,
    public postId?: number,
    public commentId?: number
  ) {}
}
