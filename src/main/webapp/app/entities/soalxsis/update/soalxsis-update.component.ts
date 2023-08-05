import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SoalxsisFormService, SoalxsisFormGroup } from './soalxsis-form.service';
import { ISoalxsis } from '../soalxsis.model';
import { SoalxsisService } from '../service/soalxsis.service';

@Component({
  selector: 'jhi-soalxsis-update',
  templateUrl: './soalxsis-update.component.html',
})
export class SoalxsisUpdateComponent implements OnInit {
  isSaving = false;
  soalxsis: ISoalxsis | null = null;

  editForm: SoalxsisFormGroup = this.soalxsisFormService.createSoalxsisFormGroup();

  constructor(
    protected soalxsisService: SoalxsisService,
    protected soalxsisFormService: SoalxsisFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soalxsis }) => {
      this.soalxsis = soalxsis;
      if (soalxsis) {
        this.updateForm(soalxsis);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soalxsis = this.soalxsisFormService.getSoalxsis(this.editForm);
    if (soalxsis.id !== null) {
      this.subscribeToSaveResponse(this.soalxsisService.update(soalxsis));
    } else {
      this.subscribeToSaveResponse(this.soalxsisService.create(soalxsis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoalxsis>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(soalxsis: ISoalxsis): void {
    this.soalxsis = soalxsis;
    this.soalxsisFormService.resetForm(this.editForm, soalxsis);
  }
}
