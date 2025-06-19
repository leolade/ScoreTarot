import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GameStore {

  static readonly GAMES_ID = 'currentsGames'

  constructor() {
  }

  store(gameId: string) {
    const currentGames: string[] = this.getStoredGames();
    if (!currentGames.includes(gameId)) {
      currentGames.push(gameId);
    }
    localStorage.setItem(GameStore.GAMES_ID, JSON.stringify(currentGames));
  }

  getStoredGames(): string[] {
    return JSON.parse(localStorage.getItem(GameStore.GAMES_ID) ?? ('[]'));
  }

  delete(gameId: string): string[] {
    let currentGames: string[] = this.getStoredGames();
    localStorage.setItem(GameStore.GAMES_ID, JSON.stringify(currentGames.filter(gId => gId !== gameId)));
    return this.getStoredGames();
  }
}
