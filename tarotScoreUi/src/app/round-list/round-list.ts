import {Component, effect, input} from '@angular/core';
import {GameDto} from '../dtos/game-dto';
import {IonCol, IonGrid, IonRow} from '@ionic/angular/standalone';

@Component({
  selector: 'app-round-list',
  imports: [
    IonGrid,
    IonRow,
    IonCol
  ],
  templateUrl: './round-list.html',
  styleUrl: './round-list.css'
})
export class RoundList {
  rounds: any[] = [];
  players: string[] = ['Léo', 'Lynianna', 'Quentin', 'Antoine', 'Alban'];


  game = input.required<GameDto>();

  constructor() {
    effect(() => {
      console.log(this.game());
    });
  }
}
