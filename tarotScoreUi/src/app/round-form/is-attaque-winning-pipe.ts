import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'isAttaqueWinning'
})
export class IsAttaqueWinningPipe implements PipeTransform {

  transform(scoreAttaque: number, nombreBouts: 0 | 1 | 2 | 3 | null): boolean {
    let scoreAEgaler = 56;
    switch (nombreBouts) {
      case 1:
        scoreAEgaler = 51;
        break;
      case 2:
        scoreAEgaler = 41;
        break;
      case 3:
        scoreAEgaler = 36;
        break;
      default:
        scoreAEgaler = 56;
        break;
    }
    return scoreAttaque >= scoreAEgaler;
  }

}
