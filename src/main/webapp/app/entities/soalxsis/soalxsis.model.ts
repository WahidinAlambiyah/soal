import dayjs from 'dayjs/esm';

export interface ISoalxsis {
  id: number;
  title?: string | null;
  description?: string | null;
  rating?: number | null;
  image?: string | null;
  created_at?: dayjs.Dayjs | null;
  updated_at?: dayjs.Dayjs | null;
}

export type NewSoalxsis = Omit<ISoalxsis, 'id'> & { id: null };
