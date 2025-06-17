import { Component } from '@angular/core';

@Component({
  selector: 'app-round-list',
  imports: [
  ],
  templateUrl: './round-list.html',
  styleUrl: './round-list.css'
})
export class RoundList {
  rounds: any[] = [];
  players: string[] = ['Léo', 'Lynianna', 'Quentin', 'Antoine', 'Alban'];

}
