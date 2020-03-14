export interface IImageMetaData {
  id?: number;
  name?: string;
  value?: string;
  imageId?: number;
}

export class ImageMetaData implements IImageMetaData {
  constructor(public id?: number, public name?: string, public value?: string, public imageId?: number) {}
}
