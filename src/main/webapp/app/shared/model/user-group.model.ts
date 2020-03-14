import { IUser } from 'app/core/user/user.model';

export interface IUserGroup {
  id?: number;
  name?: string;
  detail?: any;
  profilePicContentType?: string;
  profilePic?: any;
  users?: IUser[];
  owners?: IUser[];
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: any,
    public profilePicContentType?: string,
    public profilePic?: any,
    public users?: IUser[],
    public owners?: IUser[]
  ) {}
}
