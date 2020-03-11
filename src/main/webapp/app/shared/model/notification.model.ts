import { ContentType } from 'app/shared/model/enumerations/content-type.model';

export interface INotification {
  id?: number;
  content?: any;
  contentType?: ContentType;
  userLogin?: string;
  userId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public content?: any,
    public contentType?: ContentType,
    public userLogin?: string,
    public userId?: number
  ) {}
}
