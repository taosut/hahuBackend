export interface INotificationMetaData {
  id?: number;
  name?: string;
  value?: string;
  blobValueContentType?: string;
  blobValue?: any;
  notificationId?: number;
}

export class NotificationMetaData implements INotificationMetaData {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public blobValueContentType?: string,
    public blobValue?: any,
    public notificationId?: number
  ) {}
}
