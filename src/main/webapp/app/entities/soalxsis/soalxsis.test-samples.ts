import dayjs from 'dayjs/esm';

import { ISoalxsis, NewSoalxsis } from './soalxsis.model';

export const sampleWithRequiredData: ISoalxsis = {
  id: 31887,
  title: 'compress port',
};

export const sampleWithPartialData: ISoalxsis = {
  id: 78336,
  title: 'Health Metal',
  description: 'Dakota South payment',
  rating: 57778,
  image: 'Account Licensed',
};

export const sampleWithFullData: ISoalxsis = {
  id: 18768,
  title: 'Table',
  description: 'grey Berkshire',
  rating: 77989,
  image: 'Avon',
  created_at: dayjs('2023-08-04'),
  updated_at: dayjs('2023-08-04'),
};

export const sampleWithNewData: NewSoalxsis = {
  title: 'Greenland Colorado background',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
