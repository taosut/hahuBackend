import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IViews } from 'app/shared/model/views.model';

@Component({
  selector: 'jhi-views-detail',
  templateUrl: './views-detail.component.html',
})
export class ViewsDetailComponent implements OnInit {
  views: IViews | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ views }) => (this.views = views));
  }

  previousState(): void {
    window.history.back();
  }
}
