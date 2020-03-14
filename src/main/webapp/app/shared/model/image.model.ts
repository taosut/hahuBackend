import { IImageMetaData } from 'app/shared/model/image-meta-data.model';

export interface IImage {
  id?: number;
  imageContentType?: string;
  image?: any;
  imageMetaData?: IImageMetaData[];
  albumId?: number;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public imageContentType?: string,
    public image?: any,
    public imageMetaData?: IImageMetaData[],
    public albumId?: number
  ) {}
}
