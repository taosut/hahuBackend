import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/core/language/language.constants';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';
import { Authority } from 'app/shared/constants/authority.constants';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent implements OnInit {
  school!: ISchool | null;
  languages = LANGUAGES;
  authorities: string[] = [];
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [],
    langKey: [],
    authorities: []
  });

  constructor(
    private schoolService: SchoolService,
    public activeModal: NgbActiveModal,
    private userService: UserService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
  }
  save(): void {
    this.isSaving = true;
    this.editForm.patchValue({
      authorities: [Authority.SCHOOL_ADMIN]
    });
    this.userService.create(this.editForm.value).subscribe(
      res => this.onSaveSuccess(res),
      () => this.onSaveError()
    );
  }
  private onSaveSuccess(user: IUser): void {
    if (this.school && this.school.users) {
      user.authorities = [Authority.SCHOOL_ADMIN];
      this.school.users.push(user);
      this.schoolService.update(this.school).subscribe(res => (this.school = res.body));
    }
    this.isSaving = false;
    this.activeModal.dismiss();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
