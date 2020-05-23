import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SchoolProgressService } from 'app/entities/school-progress/school-progress.service';
import { ISchoolProgress, SchoolProgress } from 'app/shared/model/school-progress.model';

describe('Service Tests', () => {
  describe('SchoolProgress Service', () => {
    let injector: TestBed;
    let service: SchoolProgressService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchoolProgress;
    let expectedResult: ISchoolProgress | ISchoolProgress[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SchoolProgressService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SchoolProgress(0, 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SchoolProgress', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SchoolProgress()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SchoolProgress', () => {
        const returnedFromService = Object.assign(
          {
            subject: 'BBBBBB',
            year: 1,
            semester: 'BBBBBB',
            result: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SchoolProgress', () => {
        const returnedFromService = Object.assign(
          {
            subject: 'BBBBBB',
            year: 1,
            semester: 'BBBBBB',
            result: 1
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

      it('should delete a SchoolProgress', () => {
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
