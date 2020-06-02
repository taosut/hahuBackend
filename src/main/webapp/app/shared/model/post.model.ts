import { Moment } from 'moment';
import { IPostMetaData } from 'app/shared/model/post-meta-data.model';
import { IComment } from 'app/shared/model/comment.model';
import { ILikes } from 'app/shared/model/likes.model';
import { IViews } from 'app/shared/model/views.model';
import { IShares } from 'app/shared/model/shares.model';
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
  popularityIndex?: number;
  trendingIndex?: number;
  postMetaData?: IPostMetaData[];
  comments?: IComment[];
  likes?: ILikes[];
  views?: IViews[];
  shares?: IShares[];
  posts?: IPost[];
  userLogin?: string;
  userId?: number;
  categories?: ICategory[];
  tags?: ITag[];
  pageId?: number;
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
    public popularityIndex?: number,
    public trendingIndex?: number,
    public postMetaData?: IPostMetaData[],
    public comments?: IComment[],
    public likes?: ILikes[],
    public views?: IViews[],
    public shares?: IShares[],
    public posts?: IPost[],
    public userLogin?: string,
    public userId?: number,
    public categories?: ICategory[],
    public tags?: ITag[],
    public pageId?: number
  ) {}
}
