import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISoalxsis } from '../soalxsis.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../soalxsis.test-samples';

import { SoalxsisService, RestSoalxsis } from './soalxsis.service';

const requireRestSample: RestSoalxsis = {
  ...sampleWithRequiredData,
  created_at: sampleWithRequiredData.created_at?.format(DATE_FORMAT),
  updated_at: sampleWithRequiredData.updated_at?.format(DATE_FORMAT),
};

describe('Soalxsis Service', () => {
  let service: SoalxsisService;
  let httpMock: HttpTestingController;
  let expectedResult: ISoalxsis | ISoalxsis[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SoalxsisService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Soalxsis', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const soalxsis = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(soalxsis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Soalxsis', () => {
      const soalxsis = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(soalxsis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Soalxsis', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Soalxsis', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Soalxsis', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSoalxsisToCollectionIfMissing', () => {
      it('should add a Soalxsis to an empty array', () => {
        const soalxsis: ISoalxsis = sampleWithRequiredData;
        expectedResult = service.addSoalxsisToCollectionIfMissing([], soalxsis);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soalxsis);
      });

      it('should not add a Soalxsis to an array that contains it', () => {
        const soalxsis: ISoalxsis = sampleWithRequiredData;
        const soalxsisCollection: ISoalxsis[] = [
          {
            ...soalxsis,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSoalxsisToCollectionIfMissing(soalxsisCollection, soalxsis);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Soalxsis to an array that doesn't contain it", () => {
        const soalxsis: ISoalxsis = sampleWithRequiredData;
        const soalxsisCollection: ISoalxsis[] = [sampleWithPartialData];
        expectedResult = service.addSoalxsisToCollectionIfMissing(soalxsisCollection, soalxsis);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soalxsis);
      });

      it('should add only unique Soalxsis to an array', () => {
        const soalxsisArray: ISoalxsis[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const soalxsisCollection: ISoalxsis[] = [sampleWithRequiredData];
        expectedResult = service.addSoalxsisToCollectionIfMissing(soalxsisCollection, ...soalxsisArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const soalxsis: ISoalxsis = sampleWithRequiredData;
        const soalxsis2: ISoalxsis = sampleWithPartialData;
        expectedResult = service.addSoalxsisToCollectionIfMissing([], soalxsis, soalxsis2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soalxsis);
        expect(expectedResult).toContain(soalxsis2);
      });

      it('should accept null and undefined values', () => {
        const soalxsis: ISoalxsis = sampleWithRequiredData;
        expectedResult = service.addSoalxsisToCollectionIfMissing([], null, soalxsis, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soalxsis);
      });

      it('should return initial array if no Soalxsis is added', () => {
        const soalxsisCollection: ISoalxsis[] = [sampleWithRequiredData];
        expectedResult = service.addSoalxsisToCollectionIfMissing(soalxsisCollection, undefined, null);
        expect(expectedResult).toEqual(soalxsisCollection);
      });
    });

    describe('compareSoalxsis', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSoalxsis(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSoalxsis(entity1, entity2);
        const compareResult2 = service.compareSoalxsis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSoalxsis(entity1, entity2);
        const compareResult2 = service.compareSoalxsis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSoalxsis(entity1, entity2);
        const compareResult2 = service.compareSoalxsis(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
