export interface ISchoolProgress {
  id?: number;
  subject?: string;
  year?: number;
  semester?: string;
  result?: number;
  userLogin?: string;
  userId?: number;
}

export class SchoolProgress implements ISchoolProgress {
  constructor(
    public id?: number,
    public subject?: string,
    public year?: number,
    public semester?: string,
    public result?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
