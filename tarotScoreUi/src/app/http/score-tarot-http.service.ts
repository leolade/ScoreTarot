import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GameDto} from '../dtos/game-dto';
import {RoundDto} from '../dtos/round-dto';

@Injectable({
  providedIn: 'root'
})
export class ScoreTarotHttpService {

  httpClient: HttpClient = inject(HttpClient);

  getGame(gameId: string): Observable<GameDto> {
    return this.httpClient.get<GameDto>(`http://192.168.1.39:8080/tarot/score/game/${gameId}`);
  }

  newGame(): Observable<string> {
    return this.httpClient.post(`http://192.168.1.39:8080/tarot/score/game`, undefined, {responseType: 'text'});
  }

  newRound(gameId: string, round: RoundDto): Observable<void> {
    return this.httpClient.post<void>(`http://192.168.1.39:8080/tarot/score/game/${gameId}/round`, round);
  }
}
