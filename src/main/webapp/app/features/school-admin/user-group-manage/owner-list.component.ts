import {Component, Input, OnInit} from '@angular/core';
import {IUserGroup} from "app/shared/model/user-group.model";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserGroupService} from "./user-group.service";
import {UserManagementComponent} from "./user-management.component";
import {IUser} from "app/core/user/user.model";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'jhi-owner-list',
  templateUrl: './owner-list.component.html',
})
export class OwnerListComponent implements OnInit {
  @Input() userGroup!: IUserGroup| null;
  constructor(protected modalService: NgbModal, protected userGroupService: UserGroupService) { }

  ngOnInit(): void {
  }
  addFromExisting(): void {
    const modalRef = this.modalService.open(UserManagementComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userGroup = this.userGroup;
    modalRef.componentInstance.ownerSelection = true;
  }
  removeUser(user: IUser): void{
    if (this.userGroup && this.userGroup.owners) {
      this.userGroup.owners.splice(this.userGroup.owners.indexOf(user), 1);
      this.subscribeToSaveResponse(this.userGroupService.update(this.userGroup));
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroup>>): void {
    result.subscribe(
      res => this.onSaveSuccess(res.body),
      () => this.onSaveError()
    );
  }
  protected onSaveSuccess(userGroup: IUserGroup |null): void {
    this.userGroup = userGroup;
  }

  protected onSaveError(): void {
  }
}
