import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GameDto} from '../dtos/game-dto';
import {RoundDto} from '../dtos/round-dto';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ScoreTarotHttpService {

  httpClient: HttpClient = inject(HttpClient);

  getGame(gameId: string): Observable<GameDto> {
    return this.httpClient.get<GameDto>(`${environment.apiURL}/tarot/score/game/${gameId}`);
  }

  newGame(): Observable<string> {
    return this.httpClient.post(`${environment.apiURL}/tarot/score/game`, undefined, {responseType: 'text'});
  }

  newRound(gameId: string, round: RoundDto): Observable<void> {
    return this.httpClient.post<void>(`${environment.apiURL}/tarot/score/game/${gameId}/round`, round);
  }

  getRound(roundId: string): Observable<RoundDto> {
    return this.httpClient.get<RoundDto>(`${environment.apiURL}/tarot/score/round/${roundId}`);
  }

  editRound(roundId: string, round: RoundDto): Observable<void> {
    return this.httpClient.put<void>(`${environment.apiURL}/tarot/score/round/${roundId}`, round);
  }

  deleteRound(roundId: string) {
    return this.httpClient.delete(`${environment.apiURL}/tarot/score/round/${roundId}`);
  }

  deleteGame(gameId: string): Observable<void> {
    return this.httpClient.delete<void>(`${environment.apiURL}/tarot/score/game/${gameId}`);
  }
}
