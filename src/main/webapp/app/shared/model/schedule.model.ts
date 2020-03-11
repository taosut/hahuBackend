import { Moment } from 'moment';

export interface ISchedule {
  id?: number;
  title?: string;
  startTime?: Moment;
  endTime?: Moment;
  userLogin?: string;
  userId?: number;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public title?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public userLogin?: string,
    public userId?: number
  ) {}
}
