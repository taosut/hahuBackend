import { IUser } from 'app/core/user/user.model';

export interface IFamily {
  id?: number;
  hasFamilyControl?: boolean;
  userLogin?: string;
  userId?: number;
  parents?: IUser[];
}

export class Family implements IFamily {
  constructor(
    public id?: number,
    public hasFamilyControl?: boolean,
    public userLogin?: string,
    public userId?: number,
    public parents?: IUser[]
  ) {
    this.hasFamilyControl = this.hasFamilyControl || false;
  }
}
