import {Component, computed, effect, inject, input, linkedSignal, signal, Signal, WritableSignal} from '@angular/core';
import {GameDto} from '../dtos/game-dto';
import {
  IonActionSheet,
  IonButton,
  IonButtons,
  IonCol,
  IonFab,
  IonFabButton,
  IonGrid,
  IonHeader,
  IonIcon,
  IonItemDivider,
  IonLabel,
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
    IonLabel,
    IonActionSheet
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
  actionSheetOpened: WritableSignal<string> = signal('');
  actionSheetButtons = [
    {
      text: 'Supprimer la partie',
      role: 'destructive',
      data: {
        action: 'delete',
      },
    },
    {
      text: 'Modifier la partie',
      role: 'edit',
      data: {
        action: 'edit',
      },
    },
    {
      text: 'Cancel',
      role: 'cancel',
      data: {
        action: 'cancel',
      },
    },
  ];

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

  onRowClickHandler(id: string | undefined) {
    if (!id) {
      return;
    }
    this.actionSheetOpened.set(id);
  }

  onDismissClickHandler($event: any, id: string | undefined) {
    if (!id) {
      return;
    }
    this.actionSheetOpened.set('');
    switch ($event.detail.role) {
      case 'destructive':
        this.onDeleteClickHandler(id)
        break;
      case 'edit':
        this.onEditClickHandler(id);
        break;
      default:
        break;
    }
  }
}
