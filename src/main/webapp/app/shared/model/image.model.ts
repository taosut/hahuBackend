export interface IImage {
  id?: number;
  imageContentType?: string;
  image?: any;
  albumId?: number;
}

export class Image implements IImage {
  constructor(public id?: number, public imageContentType?: string, public image?: any, public albumId?: number) {}
}
