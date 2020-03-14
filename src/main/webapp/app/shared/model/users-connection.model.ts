import { Moment } from 'moment';

export interface IUsersConnection {
  id?: number;
  registeredTime?: Moment;
  followerLogin?: string;
  followerId?: number;
  followingLogin?: string;
  followingId?: number;
}

export class UsersConnection implements IUsersConnection {
  constructor(
    public id?: number,
    public registeredTime?: Moment,
    public followerLogin?: string,
    public followerId?: number,
    public followingLogin?: string,
    public followingId?: number
  ) {}
}
