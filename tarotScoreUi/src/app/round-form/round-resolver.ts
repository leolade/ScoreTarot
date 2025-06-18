import {ResolveFn} from '@angular/router';
import {GameDto} from '../dtos/game-dto';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';
import {inject} from '@angular/core';
import {RoundDto} from '../dtos/round-dto';

export const roundResolver: ResolveFn<RoundDto> = (route, state) => {
  const httpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);
  return httpService.getRound(route.params['roundId']);
};
