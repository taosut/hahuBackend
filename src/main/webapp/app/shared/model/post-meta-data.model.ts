export interface IPostMetaData {
  id?: number;
  name?: string;
  value?: string;
  blobValueContentType?: string;
  blobValue?: any;
  postId?: number;
}

export class PostMetaData implements IPostMetaData {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public blobValueContentType?: string,
    public blobValue?: any,
    public postId?: number
  ) {}
}
