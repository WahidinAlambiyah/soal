import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISoalxsis } from '../soalxsis.model';
import { SoalxsisService } from '../service/soalxsis.service';

@Injectable({ providedIn: 'root' })
export class SoalxsisRoutingResolveService implements Resolve<ISoalxsis | null> {
  constructor(protected service: SoalxsisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISoalxsis | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((soalxsis: HttpResponse<ISoalxsis>) => {
          if (soalxsis.body) {
            return of(soalxsis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
