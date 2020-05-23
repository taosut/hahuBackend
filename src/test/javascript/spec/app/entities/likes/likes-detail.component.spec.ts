import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { LikesDetailComponent } from 'app/entities/likes/likes-detail.component';
import { Likes } from 'app/shared/model/likes.model';

describe('Component Tests', () => {
  describe('Likes Management Detail Component', () => {
    let comp: LikesDetailComponent;
    let fixture: ComponentFixture<LikesDetailComponent>;
    const route = ({ data: of({ likes: new Likes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [LikesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LikesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LikesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load likes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.likes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
