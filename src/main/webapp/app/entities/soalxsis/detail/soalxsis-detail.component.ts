import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoalxsis } from '../soalxsis.model';

@Component({
  selector: 'jhi-soalxsis-detail',
  templateUrl: './soalxsis-detail.component.html',
})
export class SoalxsisDetailComponent implements OnInit {
  soalxsis: ISoalxsis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soalxsis }) => {
      this.soalxsis = soalxsis;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
