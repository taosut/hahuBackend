import { IUser } from 'app/core/user/user.model';

export interface IUserGroup {
  id?: number;
  groupName?: string;
  users?: IUser[];
}

export class UserGroup implements IUserGroup {
  constructor(public id?: number, public groupName?: string, public users?: IUser[]) {}
}
