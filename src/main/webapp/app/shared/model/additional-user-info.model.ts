import { IUsersConnection } from 'app/shared/model/users-connection.model';

export interface IAdditionalUserInfo {
  id?: number;
  phone?: string;
  profilePicture?: string;
  userLogin?: string;
  userId?: number;
  followings?: IUsersConnection[];
  followers?: IUsersConnection[];
  userGroupId?: number;
}

export class AdditionalUserInfo implements IAdditionalUserInfo {
  constructor(
    public id?: number,
    public phone?: string,
    public profilePicture?: string,
    public userLogin?: string,
    public userId?: number,
    public followings?: IUsersConnection[],
    public followers?: IUsersConnection[],
    public userGroupId?: number
  ) {}
}
