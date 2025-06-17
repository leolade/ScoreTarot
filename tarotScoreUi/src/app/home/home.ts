import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, of} from 'rxjs';
import {
  IonButton, IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardTitle,
  IonHeader,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {ScoreTarotHttpService} from '../http/score-tarot-http.service';

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
    IonCard
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  scoreTarotHttpService: ScoreTarotHttpService = inject(ScoreTarotHttpService);
  router: Router = inject(Router);

  onNewGameClickHandler() {
    this.scoreTarotHttpService.newGame()
      .subscribe(
        (id: string) => {
          this.router.navigate([id.replace(/"/g, ''), 'rounds']).then();
        }
      )
  }
}
