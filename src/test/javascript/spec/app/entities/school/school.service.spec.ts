import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SchoolService } from 'app/entities/school/school.service';
import { ISchool, School } from 'app/shared/model/school.model';
import { ContentType } from 'app/shared/model/enumerations/content-type.model';

describe('Service Tests', () => {
  describe('School Service', () => {
    let injector: TestBed;
    let service: SchoolService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchool;
    let expectedResult: ISchool | ISchool[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SchoolService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new School(
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        ContentType.TEXT,
        'AAAAAAA',
        ContentType.TEXT
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a School', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new School()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a School', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            featuredImage: 'BBBBBB',
            phone: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            about: 'BBBBBB',
            aboutType: 'BBBBBB',
            location: 'BBBBBB',
            locationType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of School', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            featuredImage: 'BBBBBB',
            phone: 'BBBBBB',
            email: 'BBBBBB',
            website: 'BBBBBB',
            about: 'BBBBBB',
            aboutType: 'BBBBBB',
            location: 'BBBBBB',
            locationType: 'BBBBBB',
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

      it('should delete a School', () => {
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
