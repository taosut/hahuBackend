export interface ISchoolProgress {
  id?: number;
  subject?: string;
  year?: number;
  semester?: string;
  result?: number;
  userLogin?: string;
  userId?: number;
  userGroupName?: string;
  userGroupId?: number;
}

export class SchoolProgress implements ISchoolProgress {
  constructor(
    public id?: number,
    public subject?: string,
    public year?: number,
    public semester?: string,
    public result?: number,
    public userLogin?: string,
    public userId?: number,
    public userGroupName?: string,
    public userGroupId?: number
  ) {}
}
