import { IUser } from 'app/core/user/user.model';

export interface IProfile {
  id?: number;
  phone?: string;
  userLogin?: string;
  userId?: number;
  families?: IUser[];
}

export class Profile implements IProfile {
  constructor(public id?: number, public phone?: string, public userLogin?: string, public userId?: number, public families?: IUser[]) {}
}
