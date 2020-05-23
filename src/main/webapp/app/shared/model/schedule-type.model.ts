import { ISchedule } from 'app/shared/model/schedule.model';

export interface IScheduleType {
  id?: number;
  name?: string;
  schedules?: ISchedule[];
}

export class ScheduleType implements IScheduleType {
  constructor(public id?: number, public name?: string, public schedules?: ISchedule[]) {}
}
