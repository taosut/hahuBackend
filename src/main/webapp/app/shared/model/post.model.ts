import { Moment } from 'moment';
import { IPostMetaData } from 'app/shared/model/post-meta-data.model';
import { IComment } from 'app/shared/model/comment.model';
import { ILikes } from 'app/shared/model/likes.model';
import { ICategory } from 'app/shared/model/category.model';
import { ITag } from 'app/shared/model/tag.model';
import { ContentType } from 'app/shared/model/enumerations/content-type.model';
import { PostType } from 'app/shared/model/enumerations/post-type.model';

export interface IPost {
  id?: number;
  title?: string;
  content?: any;
  contentType?: ContentType;
  postType?: PostType;
  featuredImageContentType?: string;
  featuredImage?: any;
  postedDate?: Moment;
  modifiedDate?: Moment;
  instantPostEndDate?: Moment;
  postMetaData?: IPostMetaData[];
  comments?: IComment[];
  likes?: ILikes[];
  userLogin?: string;
  userId?: number;
  categories?: ICategory[];
  tags?: ITag[];
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public contentType?: ContentType,
    public postType?: PostType,
    public featuredImageContentType?: string,
    public featuredImage?: any,
    public postedDate?: Moment,
    public modifiedDate?: Moment,
    public instantPostEndDate?: Moment,
    public postMetaData?: IPostMetaData[],
    public comments?: IComment[],
    public likes?: ILikes[],
    public userLogin?: string,
    public userId?: number,
    public categories?: ICategory[],
    public tags?: ITag[]
  ) {}
}
