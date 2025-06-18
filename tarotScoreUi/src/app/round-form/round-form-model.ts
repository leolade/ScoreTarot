import {PlayerDto} from '../dtos/player-dto';
import {ChelemStatus, ContractType, PetitAuBoutStatus} from '../dtos/round-dto';
import {FormControl} from '@angular/forms';

export interface RoundFormModel {
  id: FormControl<string | null>;
  contractType: FormControl<ContractType | null>;
  petitStatus: FormControl<PetitAuBoutStatus>;
  chelemStatus: FormControl<ChelemStatus | null>;
  scoreAttaque: FormControl<number>;
  nombreBouts: FormControl<0 | 1 | 2 | 3 | null>;
  taker: FormControl<PlayerDto | null>;
  called: FormControl<PlayerDto | null>;
  gameId: FormControl<string | null>;
  players: FormControl<PlayerDto[]>;
  poigneeSimples: FormControl<PlayerDto[]>;
  poigneeDoubles: FormControl<PlayerDto[]>;
  poigneeTriple: FormControl<PlayerDto[]>;
  misereSimples: FormControl<PlayerDto[]>;
  misereDoubles: FormControl<PlayerDto[]>;
}
