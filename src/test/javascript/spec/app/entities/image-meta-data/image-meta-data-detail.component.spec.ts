import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { ImageMetaDataDetailComponent } from 'app/entities/image-meta-data/image-meta-data-detail.component';
import { ImageMetaData } from 'app/shared/model/image-meta-data.model';

describe('Component Tests', () => {
  describe('ImageMetaData Management Detail Component', () => {
    let comp: ImageMetaDataDetailComponent;
    let fixture: ComponentFixture<ImageMetaDataDetailComponent>;
    const route = ({ data: of({ imageMetaData: new ImageMetaData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [ImageMetaDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImageMetaDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImageMetaDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load imageMetaData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imageMetaData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
