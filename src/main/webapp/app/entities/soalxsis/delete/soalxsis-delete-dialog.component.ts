import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISoalxsis } from '../soalxsis.model';
import { SoalxsisService } from '../service/soalxsis.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './soalxsis-delete-dialog.component.html',
})
export class SoalxsisDeleteDialogComponent {
  soalxsis?: ISoalxsis;

  constructor(protected soalxsisService: SoalxsisService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.soalxsisService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
