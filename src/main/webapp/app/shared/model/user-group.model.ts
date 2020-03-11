import { IAdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

export interface IUserGroup {
  id?: number;
  groupName?: string;
  additionalUserInfos?: IAdditionalUserInfo[];
}

export class UserGroup implements IUserGroup {
  constructor(public id?: number, public groupName?: string, public additionalUserInfos?: IAdditionalUserInfo[]) {}
}
