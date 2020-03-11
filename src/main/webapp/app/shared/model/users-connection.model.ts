import { Moment } from 'moment';

export interface IUsersConnection {
  id?: number;
  registeredTime?: Moment;
  followerId?: number;
  followingId?: number;
}

export class UsersConnection implements IUsersConnection {
  constructor(public id?: number, public registeredTime?: Moment, public followerId?: number, public followingId?: number) {}
}
