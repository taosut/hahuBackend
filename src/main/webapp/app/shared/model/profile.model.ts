export interface IProfile {
  id?: number;
  name?: string;
  value?: string;
  userLogin?: string;
  userId?: number;
}

export class Profile implements IProfile {
  constructor(public id?: number, public name?: string, public value?: string, public userLogin?: string, public userId?: number) {}
}
