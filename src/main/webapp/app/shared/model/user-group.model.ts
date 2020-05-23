import { INotification } from 'app/shared/model/notification.model';
import { ISchedule } from 'app/shared/model/schedule.model';
import { IUser } from 'app/core/user/user.model';
import { GroupType } from 'app/shared/model/enumerations/group-type.model';

export interface IUserGroup {
  id?: number;
  name?: string;
  detail?: any;
  profilePicContentType?: string;
  profilePic?: any;
  groupType?: GroupType;
  notifications?: INotification[];
  schedules?: ISchedule[];
  users?: IUser[];
  owners?: IUser[];
  schoolName?: string;
  schoolId?: number;
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: any,
    public profilePicContentType?: string,
    public profilePic?: any,
    public groupType?: GroupType,
    public notifications?: INotification[],
    public schedules?: ISchedule[],
    public users?: IUser[],
    public owners?: IUser[],
    public schoolName?: string,
    public schoolId?: number
  ) {}
}
