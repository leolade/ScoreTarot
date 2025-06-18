import {Component, effect, inject, input} from '@angular/core';
import {GameDto} from '../dtos/game-dto';
import {
  IonButton,
  IonButtons,
  IonCol,
  IonFab,
  IonFabButton,
  IonGrid,
  IonHeader,
  IonIcon,
  IonRow,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

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
    IonButton
  ],
  templateUrl: './round-list.html',
  styleUrl: './round-list.css'
})
export class RoundList {
  rounds: any[] = [];
  players: string[] = ['Léo', 'Lynianna', 'Quentin', 'Antoine', 'Alban'];

  router: Router = inject(Router);
  location: Location = inject(Location);
  game = input.required<GameDto>();

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
}
