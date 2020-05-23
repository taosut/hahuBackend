import { Moment } from 'moment';
import { INotificationMetaData } from 'app/shared/model/notification-meta-data.model';
import { ContentType } from 'app/shared/model/enumerations/content-type.model';

export interface INotification {
  id?: number;
  featuredImageContentType?: string;
  featuredImage?: any;
  title?: string;
  content?: any;
  contentType?: ContentType;
  link?: string;
  date?: Moment;
  markAsRead?: boolean;
  notificationMetaData?: INotificationMetaData[];
  userLogin?: string;
  userId?: number;
  userGroupName?: string;
  userGroupId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public featuredImageContentType?: string,
    public featuredImage?: any,
    public title?: string,
    public content?: any,
    public contentType?: ContentType,
    public link?: string,
    public date?: Moment,
    public markAsRead?: boolean,
    public notificationMetaData?: INotificationMetaData[],
    public userLogin?: string,
    public userId?: number,
    public userGroupName?: string,
    public userGroupId?: number
  ) {
    this.markAsRead = this.markAsRead || false;
  }
}
