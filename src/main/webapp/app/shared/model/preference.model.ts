import { ICategory } from 'app/shared/model/category.model';

export interface IPreference {
  id?: number;
  hasPrefereceSelected?: boolean;
  userLogin?: string;
  userId?: number;
  categories?: ICategory[];
}

export class Preference implements IPreference {
  constructor(
    public id?: number,
    public hasPrefereceSelected?: boolean,
    public userLogin?: string,
    public userId?: number,
    public categories?: ICategory[]
  ) {
    this.hasPrefereceSelected = this.hasPrefereceSelected || false;
  }
}
