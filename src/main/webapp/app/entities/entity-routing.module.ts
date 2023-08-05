import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'soalxsis',
        data: { pageTitle: 'soalApp.soalxsis.home.title' },
        loadChildren: () => import('./soalxsis/soalxsis.module').then(m => m.SoalxsisModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
