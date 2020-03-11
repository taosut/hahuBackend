import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HahuTestModule } from '../../../test.module';
import { UsersConnectionDetailComponent } from 'app/entities/users-connection/users-connection-detail.component';
import { UsersConnection } from 'app/shared/model/users-connection.model';

describe('Component Tests', () => {
  describe('UsersConnection Management Detail Component', () => {
    let comp: UsersConnectionDetailComponent;
    let fixture: ComponentFixture<UsersConnectionDetailComponent>;
    const route = ({ data: of({ usersConnection: new UsersConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HahuTestModule],
        declarations: [UsersConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsersConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsersConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usersConnection on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usersConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
