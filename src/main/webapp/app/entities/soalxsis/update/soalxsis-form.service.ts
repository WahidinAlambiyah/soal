import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISoalxsis, NewSoalxsis } from '../soalxsis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISoalxsis for edit and NewSoalxsisFormGroupInput for create.
 */
type SoalxsisFormGroupInput = ISoalxsis | PartialWithRequiredKeyOf<NewSoalxsis>;

type SoalxsisFormDefaults = Pick<NewSoalxsis, 'id'>;

type SoalxsisFormGroupContent = {
  id: FormControl<ISoalxsis['id'] | NewSoalxsis['id']>;
  title: FormControl<ISoalxsis['title']>;
  description: FormControl<ISoalxsis['description']>;
  rating: FormControl<ISoalxsis['rating']>;
  image: FormControl<ISoalxsis['image']>;
  created_at: FormControl<ISoalxsis['created_at']>;
  updated_at: FormControl<ISoalxsis['updated_at']>;
};

export type SoalxsisFormGroup = FormGroup<SoalxsisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SoalxsisFormService {
  createSoalxsisFormGroup(soalxsis: SoalxsisFormGroupInput = { id: null }): SoalxsisFormGroup {
    const soalxsisRawValue = {
      ...this.getFormDefaults(),
      ...soalxsis,
    };
    return new FormGroup<SoalxsisFormGroupContent>({
      id: new FormControl(
        { value: soalxsisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(soalxsisRawValue.title, {
        validators: [Validators.required],
      }),
      description: new FormControl(soalxsisRawValue.description),
      rating: new FormControl(soalxsisRawValue.rating),
      image: new FormControl(soalxsisRawValue.image),
      created_at: new FormControl(soalxsisRawValue.created_at),
      updated_at: new FormControl(soalxsisRawValue.updated_at),
    });
  }

  getSoalxsis(form: SoalxsisFormGroup): ISoalxsis | NewSoalxsis {
    return form.getRawValue() as ISoalxsis | NewSoalxsis;
  }

  resetForm(form: SoalxsisFormGroup, soalxsis: SoalxsisFormGroupInput): void {
    const soalxsisRawValue = { ...this.getFormDefaults(), ...soalxsis };
    form.reset(
      {
        ...soalxsisRawValue,
        id: { value: soalxsisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SoalxsisFormDefaults {
    return {
      id: null,
    };
  }
}
