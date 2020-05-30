import {Component, Input, OnInit} from '@angular/core';
import {IUserGroup} from "app/shared/model/user-group.model";

@Component({
  selector: 'jhi-owner-list',
  templateUrl: './owner-list.component.html',
})
export class OwnerListComponent implements OnInit {
  @Input() userGroup!: IUserGroup| null;
  constructor() { }

  ngOnInit(): void {
  }

}
