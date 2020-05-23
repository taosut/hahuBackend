import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleType } from 'app/shared/model/schedule-type.model';

@Component({
  selector: 'jhi-schedule-type-detail',
  templateUrl: './schedule-type-detail.component.html',
})
export class ScheduleTypeDetailComponent implements OnInit {
  scheduleType: IScheduleType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleType }) => (this.scheduleType = scheduleType));
  }

  previousState(): void {
    window.history.back();
  }
}
