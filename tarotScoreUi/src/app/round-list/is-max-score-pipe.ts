import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'isMaxScore'
})
export class IsMaxScorePipe implements PipeTransform {

  transform(score: number, scores: {[p in string]: number}): unknown {
    return Math.abs(score) === Math.max(...(Object.values(scores)).map(s => Math.abs(s)));
  }

}
