import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../soalxsis.test-samples';

import { SoalxsisFormService } from './soalxsis-form.service';

describe('Soalxsis Form Service', () => {
  let service: SoalxsisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoalxsisFormService);
  });

  describe('Service methods', () => {
    describe('createSoalxsisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSoalxsisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            rating: expect.any(Object),
            image: expect.any(Object),
            created_at: expect.any(Object),
            updated_at: expect.any(Object),
          })
        );
      });

      it('passing ISoalxsis should create a new form with FormGroup', () => {
        const formGroup = service.createSoalxsisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            rating: expect.any(Object),
            image: expect.any(Object),
            created_at: expect.any(Object),
            updated_at: expect.any(Object),
          })
        );
      });
    });

    describe('getSoalxsis', () => {
      it('should return NewSoalxsis for default Soalxsis initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSoalxsisFormGroup(sampleWithNewData);

        const soalxsis = service.getSoalxsis(formGroup) as any;

        expect(soalxsis).toMatchObject(sampleWithNewData);
      });

      it('should return NewSoalxsis for empty Soalxsis initial value', () => {
        const formGroup = service.createSoalxsisFormGroup();

        const soalxsis = service.getSoalxsis(formGroup) as any;

        expect(soalxsis).toMatchObject({});
      });

      it('should return ISoalxsis', () => {
        const formGroup = service.createSoalxsisFormGroup(sampleWithRequiredData);

        const soalxsis = service.getSoalxsis(formGroup) as any;

        expect(soalxsis).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISoalxsis should not enable id FormControl', () => {
        const formGroup = service.createSoalxsisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSoalxsis should disable id FormControl', () => {
        const formGroup = service.createSoalxsisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
