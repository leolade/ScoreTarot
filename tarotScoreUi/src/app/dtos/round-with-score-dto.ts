import {RoundDto} from './round-dto';

export interface RoundWithScoreDto extends RoundDto {
  scores: { [playerId: string]: number };
}
