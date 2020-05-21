import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SchoolUpdateComponent } from 'app/features/system-admin/school/school-update.component';

@Component({
  selector: 'jhi-school-detail',
  templateUrl: './school-detail.component.html'
})
export class SchoolDetailComponent implements OnInit {
  school: ISchool | null = null;
  isSaving = false;
  showBack = false;

  constructor(
    protected modalService: NgbModal,
    protected schoolService: SchoolService,
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.showBack = +(this.activatedRoute.snapshot.paramMap.get('quantity') || 0) !== 1;
    this.activatedRoute.data.subscribe(({ school }) => (this.school = school));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
  editSchool(): void {
    const modalRef = this.modalService.open(SchoolUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.school = this.school;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchool>>): void {
    result.subscribe(
      res => this.onSaveSuccess(res.body),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(school: ISchool | null): void {
    this.isSaving = false;
    this.school = school;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
