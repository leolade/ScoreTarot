import {Routes} from '@angular/router';
import {gameRoundsResolver} from './round-list/game-rounds-resolver';

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
    resolve: {
      game: gameRoundsResolver
    }
  }
];
