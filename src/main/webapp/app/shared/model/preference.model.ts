export interface IPreference {
  id?: number;
  name?: string;
  value?: string;
  userLogin?: string;
  userId?: number;
}

export class Preference implements IPreference {
  constructor(public id?: number, public name?: string, public value?: string, public userLogin?: string, public userId?: number) {}
}
