import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SoalxsisComponent } from './list/soalxsis.component';
import { SoalxsisDetailComponent } from './detail/soalxsis-detail.component';
import { SoalxsisUpdateComponent } from './update/soalxsis-update.component';
import { SoalxsisDeleteDialogComponent } from './delete/soalxsis-delete-dialog.component';
import { SoalxsisRoutingModule } from './route/soalxsis-routing.module';

@NgModule({
  imports: [SharedModule, SoalxsisRoutingModule],
  declarations: [SoalxsisComponent, SoalxsisDetailComponent, SoalxsisUpdateComponent, SoalxsisDeleteDialogComponent],
})
export class SoalxsisModule {}
