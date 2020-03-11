export interface ISetting {
  id?: number;
  name?: string;
  value?: string;
  userLogin?: string;
  userId?: number;
}

export class Setting implements ISetting {
  constructor(public id?: number, public name?: string, public value?: string, public userLogin?: string, public userId?: number) {}
}
