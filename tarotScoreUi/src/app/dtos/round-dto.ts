
import {PlayerDto} from './player-dto';

export type ContractType = 'PETITE' | 'GARDE' | 'GARDE_SANS' | 'GARDE_CONTRE';
export type PetitAuBoutStatus = 'NON' | 'GAGNE' | 'PERDU';
export type ChelemStatus = 'NON' | 'ANNONCE_REUSSI' | 'ANNONCE_RATE' | 'REUSSI_NON_ANNONCE';

export interface RoundDto {
  id?: string;

  contractType: ContractType;
  petitStatus: PetitAuBoutStatus;
  createdDate: Date;
  chelemStatus: ChelemStatus;

  scoreAttaque: number;
  nombreBouts: 0 | 1 | 2 | 3;

  taker: PlayerDto;
  called?: PlayerDto;
  gameId: string;

  players: PlayerDto[];

  poigneeSimples: PlayerDto[];
  poigneeDoubles: PlayerDto[];
  poigneeTriple: PlayerDto[];

  misereSimples: PlayerDto[];
  misereDoubles: PlayerDto[];
}
