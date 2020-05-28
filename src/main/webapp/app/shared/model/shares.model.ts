import { Moment } from 'moment';

export interface IShares {
  id?: number;
  registeredTime?: Moment;
  userLogin?: string;
  userId?: number;
  postId?: number;
}

export class Shares implements IShares {
  constructor(
    public id?: number,
    public registeredTime?: Moment,
    public userLogin?: string,
    public userId?: number,
    public postId?: number
  ) {}
}
