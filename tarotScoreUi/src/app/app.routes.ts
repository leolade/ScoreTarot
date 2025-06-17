import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home').then(m => m.Home),
    pathMatch: 'full',
  },
  {
    path: ':gameId/rounds',
    loadComponent: () => import('./round-list/round-list').then(m => m.RoundList),
    pathMatch: 'full',
  }
];
