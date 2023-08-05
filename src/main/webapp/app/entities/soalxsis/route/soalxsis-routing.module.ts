import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SoalxsisComponent } from '../list/soalxsis.component';
import { SoalxsisDetailComponent } from '../detail/soalxsis-detail.component';
import { SoalxsisUpdateComponent } from '../update/soalxsis-update.component';
import { SoalxsisRoutingResolveService } from './soalxsis-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const soalxsisRoute: Routes = [
  {
    path: '',
    component: SoalxsisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SoalxsisDetailComponent,
    resolve: {
      soalxsis: SoalxsisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SoalxsisUpdateComponent,
    resolve: {
      soalxsis: SoalxsisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SoalxsisUpdateComponent,
    resolve: {
      soalxsis: SoalxsisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(soalxsisRoute)],
  exports: [RouterModule],
})
export class SoalxsisRoutingModule {}
