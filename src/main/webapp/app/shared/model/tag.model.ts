import { IPost } from 'app/shared/model/post.model';

export interface ITag {
  id?: number;
  name?: string;
  description?: string;
  posts?: IPost[];
}

export class Tag implements ITag {
  constructor(public id?: number, public name?: string, public description?: string, public posts?: IPost[]) {}
}
