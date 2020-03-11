import { IImage } from 'app/shared/model/image.model';

export interface IAlbum {
  id?: number;
  name?: string;
  images?: IImage[];
}

export class Album implements IAlbum {
  constructor(public id?: number, public name?: string, public images?: IImage[]) {}
}
