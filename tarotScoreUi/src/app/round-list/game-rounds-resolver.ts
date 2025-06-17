import {ResolveFn} from '@angular/router';
import {GameDto} from '../dtos/game-dto';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';
import {inject} from '@angular/core';

export const gameRoundsResolver: ResolveFn<GameDto> = (route, state) => {
  const httpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);
  return httpService.getGame(route.params['gameId']);
};
