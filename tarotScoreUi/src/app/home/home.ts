import {Component, inject, signal, WritableSignal} from '@angular/core';
import {Router} from '@angular/router';
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardTitle,
  IonContent,
  IonHeader,
  IonInput,
  IonPopover,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';
import {GameStore} from './game-store';
import {forkJoin} from 'rxjs';
import {GameDto} from '../dtos/game-dto';
import {DatePipe} from '@angular/common';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Clipboard} from '@angular/cdk/clipboard';

@Component({
  selector: 'app-home',
  imports: [
    IonToolbar,
    IonTitle,
    IonHeader,
    IonButton,
    IonCardContent,
    IonCardTitle,
    IonCardHeader,
    IonCard,
    DatePipe,
    IonInput,
    ReactiveFormsModule,
    IonPopover,
    IonContent
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  scoreTarotHttpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);
  gameStore: GameStore = inject(GameStore);
  clipboard: Clipboard = inject(Clipboard);
  router: Router = inject(Router);
  games: WritableSignal<GameDto[]> = signal([]);
  idPartieJoinFC: FormControl<string> = new FormControl();

  constructor() {
    forkJoin(
      this.gameStore.getStoredGames().map(
        (gId) => this.scoreTarotHttpService.getGame(gId)
      )
    ).subscribe(
      (games: GameDto[]) => {
        this.games.set(games);
      }
    )
  }

  onNewGameClickHandler() {
    this.scoreTarotHttpService.newGame()
      .subscribe(
        (id: string) => {
          this.gameStore.store(id.replace(/"/g, ''));
          this.router.navigate([id.replace(/"/g, ''), 'rounds']).then();
        }
      )
  }

  onResumeGameClickHandler(gameId: string) {
    this.router.navigate([gameId, 'rounds']).then();
  }

  onJoinGameClickHandler() {
    const gameId: string = this.idPartieJoinFC.value.trim();
    this.gameStore.store(gameId);
    this.router.navigate([gameId, 'rounds']).then();
  }

  onShareCodeClickHandler(id: string) {
    this.clipboard.copy(id);
  }

  onDeleteGameClickHandler(gameId: string) {
    this.gameStore.delete(gameId);
    this.games.set(this.games().filter(game => game.id !== gameId));
    this.scoreTarotHttpService.deleteGame(gameId).subscribe()
  }
}
