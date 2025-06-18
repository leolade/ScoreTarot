import {Component, computed, effect, inject, input, linkedSignal, Signal, WritableSignal} from '@angular/core';
import {GameDto} from '../dtos/game-dto';
import {
  IonButton,
  IonButtons,
  IonCol,
  IonFab,
  IonFabButton,
  IonGrid,
  IonHeader,
  IonIcon, IonItemDivider, IonLabel,
  IonRow,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {Router} from '@angular/router';
import {Location, NgClass} from '@angular/common';
import {RoundWithScoreDto} from '../dtos/round-with-score-dto';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';
import {IsMaxScorePipe} from './is-max-score-pipe';
import {SumScoreForPlayerPipe} from './sum-score-for-player-pipe';

@Component({
  selector: 'app-round-list',
  imports: [
    IonGrid,
    IonRow,
    IonCol,
    IonFabButton,
    IonIcon,
    IonFab,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButtons,
    IonButton,
    NgClass,
    IsMaxScorePipe,
    SumScoreForPlayerPipe,
    IonItemDivider,
    IonLabel
  ],
  templateUrl: './round-list.html',
  styleUrl: './round-list.css'
})
export class RoundList {
  router: Router = inject(Router);
  location: Location = inject(Location);
  tarotHttpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);
  game = input.required<GameDto>();
  rounds: WritableSignal<RoundWithScoreDto[]> = linkedSignal(() => this.game().rounds);
  scores: Signal<{ [p in string]: number }[]> = computed(() => this.rounds().map(r => r.scores))

  constructor() {
    effect(() => {
      console.log(this.game());
    });
  }

  onNewRoundClickHandler(): void {
    this.router.navigate([this.game().id, 'round', 'new']).then();
  }

  onNavigateBack() {
    this.location.back();
  }

  onEditClickHandler(roundId: string | undefined) {
    if (!roundId) {
      return;
    }
    this.router.navigate([this.game().id, 'round', roundId]).then();
  }

  onDeleteClickHandler(roundId: string | undefined) {
    if (!roundId) {
      return;
    }
    this.tarotHttpService.deleteRound(roundId).subscribe(
      () => {
        this.rounds.set(this.rounds().filter((round) => round.id !== roundId));
      }
    )
  }
}
