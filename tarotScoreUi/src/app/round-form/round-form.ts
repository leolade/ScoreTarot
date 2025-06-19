import {
  ChangeDetectorRef,
  Component,
  effect,
  inject,
  input,
  linkedSignal,
  signal,
  untracked,
  WritableSignal
} from '@angular/core';
import {GameDto} from '../dtos/game-dto';
import {PlayerDto} from '../dtos/player-dto';
import {
  IonButton,
  IonButtons,
  IonChip,
  IonHeader,
  IonIcon,
  IonInput,
  IonItem,
  IonItemDivider,
  IonItemGroup,
  IonLabel,
  IonRadio,
  IonRadioGroup,
  IonTitle,
  IonToggle,
  IonToolbar
} from '@ionic/angular/standalone';
import {AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidatorFn, Validators} from '@angular/forms';
import {ChelemStatus, ContractType, RoundDto} from '../dtos/round-dto';
import {Location, NgClass} from '@angular/common';
import {IsAttaqueWinningPipe} from './is-attaque-winning-pipe';
import {RoundFormModel} from './round-form-model';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';
import {Router} from '@angular/router';

export function arrayMinLengthValidator(minLenght: number): ValidatorFn {
  return (control: AbstractControl<unknown[]>): { [key: string]: any } | null => {
    const forbidden = control.value.length < minLenght;
    return forbidden ? {'arrayMinLength': {value: control.value}} : null;
  };
}

@Component({
  selector: 'app-round-form',
  imports: [
    IonChip,
    IonLabel,
    IonInput,
    IonButton,
    IonItemGroup,
    IonItemDivider,
    IonItem,
    ReactiveFormsModule,
    IonToggle,
    IonRadioGroup,
    IonRadio,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButtons,
    IonIcon,
    NgClass,
    IsAttaqueWinningPipe,
  ],
  templateUrl: './round-form.html',
  styleUrl: './round-form.css'
})
export class RoundForm {
  location: Location = inject(Location);
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  router: Router = inject(Router);
  scoreTarotHttpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);

  contrats: ContractType[] = ['PETITE', 'GARDE', 'GARDE_SANS', 'GARDE_CONTRE']
  game = input.required<GameDto>();
  round = input<RoundDto>();
  chelemAnnonce: WritableSignal<boolean> = signal(false);
  scoreAttaque: WritableSignal<number> = signal(50);
  chelemAnnonceFC: FormControl<boolean> = new FormControl<boolean>(false, {nonNullable: true});
  availablePlayers: WritableSignal<PlayerDto[]> = linkedSignal(() => this.game().players);
  selectedPlayers: WritableSignal<PlayerDto[]> = signal([]);
  miserePlayers: WritableSignal<PlayerDto[]> = signal([]);
  doubleMiserePlayers: WritableSignal<PlayerDto[]> = signal([]);
  poigneePlayers: WritableSignal<PlayerDto[]> = signal([]);
  doublePoigneePlayers: WritableSignal<PlayerDto[]> = signal([]);
  triplePoigneePlayers: WritableSignal<PlayerDto[]> = signal([]);

  form = new FormGroup<RoundFormModel>({
    id: new FormControl(),
    contractType: new FormControl(null, [Validators.required]),
    petitStatus: new FormControl("NON", {nonNullable: true}),
    chelemStatus: new FormControl(null, [Validators.required]),
    scoreAttaque: new FormControl(50, {validators: [Validators.required], nonNullable: true}),
    nombreBouts: new FormControl(null, [Validators.required]),
    taker: new FormControl(null, [Validators.required]),
    called: new FormControl(null),
    gameId: new FormControl(null, [Validators.required]),
    players: new FormControl([], {validators: [arrayMinLengthValidator(5)], nonNullable: true}),
    poigneeSimples: new FormControl([], {nonNullable: true}),
    poigneeDoubles: new FormControl([], {nonNullable: true}),
    poigneeTriple: new FormControl([], {nonNullable: true}),
    misereSimples: new FormControl([], {nonNullable: true}),
    misereDoubles: new FormControl([], {nonNullable: true}),
  })
  nomNouveauJoueurFC: FormControl<string> = new FormControl('', {nonNullable: true});

  constructor() {
    effect(() => {
      this.form.controls.gameId.setValue(this.game().id);
    });
    effect(() => {
      this.form.controls.players.setValue(this.selectedPlayers());
    });
    effect(() => {
      this.form.controls.misereSimples.setValue(this.miserePlayers());
    });
    effect(() => {
      this.form.controls.misereDoubles.setValue(this.doubleMiserePlayers());
    });
    effect(() => {
      this.form.controls.poigneeSimples.setValue(this.poigneePlayers());
    });
    effect(() => {
      this.form.controls.poigneeDoubles.setValue(this.doublePoigneePlayers());
    });
    effect(() => {
      this.form.controls.poigneeTriple.setValue(this.triplePoigneePlayers());
    });
    effect(() => {
      this.form.controls.chelemStatus.setValue(this.calculateChelemStatus(this.chelemAnnonce(), this.scoreAttaque()));
    });
    effect(() => {
      const round: RoundDto | undefined = this.round();
      if (!round) {
        return;
      }
      this.selectedPlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.players.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      )
      this.miserePlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.misereSimples.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      );
      this.doubleMiserePlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.misereDoubles.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      );
      this.poigneePlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.poigneeSimples.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      );
      this.doublePoigneePlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.poigneeDoubles.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      );
      this.triplePoigneePlayers.set(
        untracked(this.availablePlayers).filter(
          (availablePlayers) => {
            return round.poigneeTriple.find((roundPlauer) => roundPlauer.id === availablePlayers.id)
          }
        )
      );
      if (round.chelemStatus === 'ANNONCE_REUSSI' || round.chelemStatus === 'ANNONCE_RATE') {
        this.chelemAnnonceFC.setValue(true);
      }
      this.form.patchValue({
        id: round.id,
        contractType: round.contractType,
        petitStatus: round.petitStatus,
        scoreAttaque: round.scoreAttaque,
        nombreBouts: round.nombreBouts,
        taker: untracked(this.availablePlayers).find((availablePlayers) => availablePlayers.id === round.taker.id),
        called: round.called ? untracked(this.availablePlayers).find((availablePlayers) => availablePlayers.id === round.called?.id) : null,
      })

    });

    this.chelemAnnonceFC.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe((value: boolean) => this.chelemAnnonce.set(value))

    this.form.controls.scoreAttaque.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe((value: number) => this.scoreAttaque.set(value))
  }

  onAddPlayerClickHandler(player: PlayerDto) {
    let selectedPlayer = this.selectedPlayers();
    if (selectedPlayer.indexOf(player) >= 0) {
      selectedPlayer = selectedPlayer.filter(iterator => iterator !== player);
    } else if (selectedPlayer.length === 5) {
      return;
    } else {
      selectedPlayer.push(player);
    }
    this.selectedPlayers.set([...selectedPlayer])
  }

  onAjouterJoueurClickHandler() {
    const newPlayerName: string = this.nomNouveauJoueurFC.value.trim();
    if (!newPlayerName || this.availablePlayers().filter(a => a.name === newPlayerName).length !== 0) {
      return;
    }
    const newPlayer = {name: this.nomNouveauJoueurFC.value};
    this.availablePlayers.set([...this.availablePlayers(), newPlayer]);
    this.onAddPlayerClickHandler(newPlayer)
    this.nomNouveauJoueurFC.setValue('');
  }

  onContratSelected(contrat: ContractType) {
    this.form.controls.contractType.setValue(contrat);
  }

  togglePlayerAnnonce(player: PlayerDto, typeAnnonce: 'MISERE' | 'DOUBLE_MISERE' | 'POIGNEE' | 'DOUBLE_POIGNEE' | 'TRIPLE_POIGNEE') {
    let liste: WritableSignal<PlayerDto[]>;
    switch (typeAnnonce) {
      case 'MISERE':
        liste = this.miserePlayers;
        break;
      case 'DOUBLE_MISERE':
        liste = this.doubleMiserePlayers;
        break;
      case 'POIGNEE':
        liste = this.poigneePlayers;
        break;
      case 'DOUBLE_POIGNEE':
        liste = this.doublePoigneePlayers;
        break;
      case 'TRIPLE_POIGNEE':
        liste = this.triplePoigneePlayers;
        break;
    }
    if (!liste) {
      return;
    }

    let valeursListe: PlayerDto[] = liste();
    if (valeursListe.includes(player)) {
      valeursListe = valeursListe.filter(iterator => iterator !== player);
    } else {
      valeursListe.push(player);
    }
    liste.set(valeursListe);
    this.cdr.detectChanges();
  }

  onNavigateBack() {
    this.location.back();
  }

  onSelectnombreBouts(nombreBouts: 0 | 1 | 2 | 3) {
    this.form.controls.nombreBouts.setValue(nombreBouts)
  }

  onValidateClickHandler() {
    const roundId = this.round()?.id;
    if (!roundId) {
      this.scoreTarotHttpService.newRound(
        this.game().id,
        this.form.value as RoundDto
      ).subscribe(
        () => {
          this.onNavigateBack();
        }
      )
    } else {
      this.scoreTarotHttpService.editRound(
        roundId,
        this.form.value as RoundDto
      ).subscribe(
        () => {
          this.onNavigateBack();
        }
      )
    }
  }

  selectTaker(player: PlayerDto) {
    this.form.controls.taker.setValue(player);
  }

  selectCalled(player: PlayerDto) {
    if (player === this.form.controls.called.value) {
      this.form.controls.called.setValue(null);
      return;
    }
    this.form.controls.called.setValue(player);
  }

  private calculateChelemStatus(chelemAnnonce: boolean, scoreAttaque: number): ChelemStatus {
    if (chelemAnnonce) {
      return scoreAttaque === 91 ? "ANNONCE_REUSSI" : "ANNONCE_RATE"
    }
    return scoreAttaque === 91 ? "REUSSI_NON_ANNONCE" : "NON"
  }

  onDeletePlayerClickHandler($event: MouseEvent, player: PlayerDto) {
    if (this.selectedPlayers().indexOf(player) === -1) {
      $event.preventDefault();
      $event.stopPropagation();
    }
    this.availablePlayers.set(this.availablePlayers().filter(iterator => iterator !== player));
  }
}
