import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdditionalUserInfo } from 'app/shared/model/additional-user-info.model';

@Component({
  selector: 'jhi-additional-user-info-detail',
  templateUrl: './additional-user-info-detail.component.html'
})
export class AdditionalUserInfoDetailComponent implements OnInit {
  additionalUserInfo: IAdditionalUserInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ additionalUserInfo }) => (this.additionalUserInfo = additionalUserInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
