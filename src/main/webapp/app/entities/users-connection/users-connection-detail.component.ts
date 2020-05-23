import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsersConnection } from 'app/shared/model/users-connection.model';

@Component({
  selector: 'jhi-users-connection-detail',
  templateUrl: './users-connection-detail.component.html',
})
export class UsersConnectionDetailComponent implements OnInit {
  usersConnection: IUsersConnection | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usersConnection }) => (this.usersConnection = usersConnection));
  }

  previousState(): void {
    window.history.back();
  }
}
