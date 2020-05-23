import { IPost } from 'app/shared/model/post.model';

export interface ICategory {
  id?: number;
  name?: string;
  description?: string;
  posts?: IPost[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public description?: string, public posts?: IPost[]) {}
}
