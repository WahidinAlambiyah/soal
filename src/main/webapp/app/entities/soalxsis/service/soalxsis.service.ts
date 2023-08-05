import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoalxsis, NewSoalxsis } from '../soalxsis.model';

export type PartialUpdateSoalxsis = Partial<ISoalxsis> & Pick<ISoalxsis, 'id'>;

type RestOf<T extends ISoalxsis | NewSoalxsis> = Omit<T, 'created_at' | 'updated_at'> & {
  created_at?: string | null;
  updated_at?: string | null;
};

export type RestSoalxsis = RestOf<ISoalxsis>;

export type NewRestSoalxsis = RestOf<NewSoalxsis>;

export type PartialUpdateRestSoalxsis = RestOf<PartialUpdateSoalxsis>;

export type EntityResponseType = HttpResponse<ISoalxsis>;
export type EntityArrayResponseType = HttpResponse<ISoalxsis[]>;

@Injectable({ providedIn: 'root' })
export class SoalxsisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/soalxses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soalxsis: NewSoalxsis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soalxsis);
    return this.http
      .post<RestSoalxsis>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(soalxsis: ISoalxsis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soalxsis);
    return this.http
      .put<RestSoalxsis>(`${this.resourceUrl}/${this.getSoalxsisIdentifier(soalxsis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(soalxsis: PartialUpdateSoalxsis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soalxsis);
    return this.http
      .patch<RestSoalxsis>(`${this.resourceUrl}/${this.getSoalxsisIdentifier(soalxsis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSoalxsis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSoalxsis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSoalxsisIdentifier(soalxsis: Pick<ISoalxsis, 'id'>): number {
    return soalxsis.id;
  }

  compareSoalxsis(o1: Pick<ISoalxsis, 'id'> | null, o2: Pick<ISoalxsis, 'id'> | null): boolean {
    return o1 && o2 ? this.getSoalxsisIdentifier(o1) === this.getSoalxsisIdentifier(o2) : o1 === o2;
  }

  addSoalxsisToCollectionIfMissing<Type extends Pick<ISoalxsis, 'id'>>(
    soalxsisCollection: Type[],
    ...soalxsesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const soalxses: Type[] = soalxsesToCheck.filter(isPresent);
    if (soalxses.length > 0) {
      const soalxsisCollectionIdentifiers = soalxsisCollection.map(soalxsisItem => this.getSoalxsisIdentifier(soalxsisItem)!);
      const soalxsesToAdd = soalxses.filter(soalxsisItem => {
        const soalxsisIdentifier = this.getSoalxsisIdentifier(soalxsisItem);
        if (soalxsisCollectionIdentifiers.includes(soalxsisIdentifier)) {
          return false;
        }
        soalxsisCollectionIdentifiers.push(soalxsisIdentifier);
        return true;
      });
      return [...soalxsesToAdd, ...soalxsisCollection];
    }
    return soalxsisCollection;
  }

  protected convertDateFromClient<T extends ISoalxsis | NewSoalxsis | PartialUpdateSoalxsis>(soalxsis: T): RestOf<T> {
    return {
      ...soalxsis,
      created_at: soalxsis.created_at?.format(DATE_FORMAT) ?? null,
      updated_at: soalxsis.updated_at?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSoalxsis: RestSoalxsis): ISoalxsis {
    return {
      ...restSoalxsis,
      created_at: restSoalxsis.created_at ? dayjs(restSoalxsis.created_at) : undefined,
      updated_at: restSoalxsis.updated_at ? dayjs(restSoalxsis.updated_at) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSoalxsis>): HttpResponse<ISoalxsis> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSoalxsis[]>): HttpResponse<ISoalxsis[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
