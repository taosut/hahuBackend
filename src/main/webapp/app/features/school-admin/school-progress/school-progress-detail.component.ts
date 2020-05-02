import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolProgress } from 'app/shared/model/school-progress.model';

@Component({
  selector: 'jhi-school-progress-detail',
  templateUrl: './school-progress-detail.component.html'
})
export class SchoolProgressDetailComponent implements OnInit {
  schoolProgress: ISchoolProgress | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolProgress }) => (this.schoolProgress = schoolProgress));
  }

  previousState(): void {
    window.history.back();
  }
}
