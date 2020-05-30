import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'school-user-management',
        loadChildren: () => import('./school/school.module').then(m => m.HahuSchoolModule)
      },
      {
        path: 'school-user-group',
        loadChildren: () => import('./user-group/school.module').then(m => m.HahuSchoolModule)
      },
      {
        path: 'school-user-group-manage',
        loadChildren: () => import('./user-group-manage/user-group-manage.module').then(m => m.HahuUserGroupManageModule)
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HahuSchoolAdminModule {}
