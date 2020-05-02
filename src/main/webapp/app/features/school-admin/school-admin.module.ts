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
        loadChildren: () => import('./user-group/user-group.module').then(m => m.HahuUserGroupModule)
      },
      {
        path: 'school-notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.HahuNotificationModule)
      },
      {
        path: 'school-progress-add',
        loadChildren: () => import('./school-progress/school-progress.module').then(m => m.HahuSchoolProgressModule)
      },
      {
        path: 'school-schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.HahuScheduleModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HahuSchoolAdminModule {}
