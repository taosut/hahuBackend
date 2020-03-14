export interface IProfile {
  id?: number;
  phone?: string;
  userLogin?: string;
  userId?: number;
}

export class Profile implements IProfile {
  constructor(public id?: number, public phone?: string, public userLogin?: string, public userId?: number) {}
}
