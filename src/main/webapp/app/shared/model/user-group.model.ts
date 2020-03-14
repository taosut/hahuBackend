import { INotification } from 'app/shared/model/notification.model';
import { ISchedule } from 'app/shared/model/schedule.model';
import { IUser } from 'app/core/user/user.model';

export interface IUserGroup {
  id?: number;
  name?: string;
  detail?: any;
  profilePicContentType?: string;
  profilePic?: any;
  notifications?: INotification[];
  schedules?: ISchedule[];
  users?: IUser[];
  owners?: IUser[];
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: any,
    public profilePicContentType?: string,
    public profilePic?: any,
    public notifications?: INotification[],
    public schedules?: ISchedule[],
    public users?: IUser[],
    public owners?: IUser[]
  ) {}
}
