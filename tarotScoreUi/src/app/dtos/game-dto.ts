import {PlayerDto} from './player-dto';
import {RoundDto} from './round-dto';
import {RoundWithScoreDto} from './round-with-score-dto';

export interface GameDto {
  createdDate: Date;
  id: string;
  players: PlayerDto[];
  rounds: RoundWithScoreDto[];
}
