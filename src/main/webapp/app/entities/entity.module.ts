import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.HahuCategoryModule),
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.HahuTagModule),
      },
      {
        path: 'post',
        loadChildren: () => import('./post/post.module').then(m => m.HahuPostModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.HahuCommentModule),
      },
      {
        path: 'likes',
        loadChildren: () => import('./likes/likes.module').then(m => m.HahuLikesModule),
      },
      {
        path: 'users-connection',
        loadChildren: () => import('./users-connection/users-connection.module').then(m => m.HahuUsersConnectionModule),
      },
      {
        path: 'album',
        loadChildren: () => import('./album/album.module').then(m => m.HahuAlbumModule),
      },
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.HahuImageModule),
      },
      {
        path: 'user-group',
        loadChildren: () => import('./user-group/user-group.module').then(m => m.HahuUserGroupModule),
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.HahuNotificationModule),
      },
      {
        path: 'school-progress',
        loadChildren: () => import('./school-progress/school-progress.module').then(m => m.HahuSchoolProgressModule),
      },
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.HahuScheduleModule),
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.HahuProfileModule),
      },
      {
        path: 'post-meta-data',
        loadChildren: () => import('./post-meta-data/post-meta-data.module').then(m => m.HahuPostMetaDataModule),
      },
      {
        path: 'image-meta-data',
        loadChildren: () => import('./image-meta-data/image-meta-data.module').then(m => m.HahuImageMetaDataModule),
      },
      {
        path: 'notification-meta-data',
        loadChildren: () => import('./notification-meta-data/notification-meta-data.module').then(m => m.HahuNotificationMetaDataModule),
      },
      {
        path: 'schedule-type',
        loadChildren: () => import('./schedule-type/schedule-type.module').then(m => m.HahuScheduleTypeModule),
      },
      {
        path: 'school',
        loadChildren: () => import('./school/school.module').then(m => m.HahuSchoolModule),
      },
      {
        path: 'shares',
        loadChildren: () => import('./shares/shares.module').then(m => m.HahuSharesModule),
      },
      {
        path: 'views',
        loadChildren: () => import('./views/views.module').then(m => m.HahuViewsModule),
      },
      {
        path: 'preference',
        loadChildren: () => import('./preference/preference.module').then(m => m.HahuPreferenceModule),
      },
      {
        path: 'family',
        loadChildren: () => import('./family/family.module').then(m => m.HahuFamilyModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HahuEntityModule {}
