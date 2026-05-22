import { Routes } from '@angular/router';
import { Login } from './login/login';
import { GameList } from './game-list/game-list';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'games', component: GameList }
];
