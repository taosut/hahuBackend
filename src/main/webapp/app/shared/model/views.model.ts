import { Moment } from 'moment';

export interface IViews {
  id?: number;
  registeredTime?: Moment;
  userLogin?: string;
  userId?: number;
  postId?: number;
}

export class Views implements IViews {
  constructor(
    public id?: number,
    public registeredTime?: Moment,
    public userLogin?: string,
    public userId?: number,
    public postId?: number
  ) {}
}
