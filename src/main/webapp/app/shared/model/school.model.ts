import { IUserGroup } from 'app/shared/model/user-group.model';
import { IUser } from 'app/core/user/user.model';
import { ContentType } from 'app/shared/model/enumerations/content-type.model';

export interface ISchool {
  id?: number;
  name?: string;
  featuredImageContentType?: string;
  featuredImage?: any;
  phone?: string;
  email?: string;
  website?: string;
  about?: any;
  aboutType?: ContentType;
  location?: any;
  locationType?: ContentType;
  userGroups?: IUserGroup[];
  users?: IUser[];
}

export class School implements ISchool {
  constructor(
    public id?: number,
    public name?: string,
    public featuredImageContentType?: string,
    public featuredImage?: any,
    public phone?: string,
    public email?: string,
    public website?: string,
    public about?: any,
    public aboutType?: ContentType,
    public location?: any,
    public locationType?: ContentType,
    public userGroups?: IUserGroup[],
    public users?: IUser[]
  ) {}
}
