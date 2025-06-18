import {Pipe, PipeTransform} from '@angular/core';
import {PlayerDto} from '../dtos/player-dto';

@Pipe({
  name: 'sumScoreForPlayer'
})
export class SumScoreForPlayerPipe implements PipeTransform {

  transform(scores: { [p in string]: number }[], player: PlayerDto): number {
    const playerId: string | undefined = player.id;
    if (playerId === undefined) {
      return 0;
    }
    return scores
      .map((score) => score[playerId])
      .reduce((a, b) => (a ?? 0) + (b ?? 0), 0);
  }

}
