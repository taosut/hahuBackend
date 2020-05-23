import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IUserGroup, UserGroup } from 'app/shared/model/user-group.model';
import { GroupType } from 'app/shared/model/enumerations/group-type.model';

describe('Service Tests', () => {
  describe('UserGroup Service', () => {
    let injector: TestBed;
    let service: UserGroupService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserGroup;
    let expectedResult: IUserGroup | IUserGroup[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserGroupService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserGroup(0, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', GroupType.PUBLIC);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserGroup()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserGroup', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            profilePic: 'BBBBBB',
            groupType: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserGroup', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            detail: 'BBBBBB',
            profilePic: 'BBBBBB',
            groupType: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserGroup', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
