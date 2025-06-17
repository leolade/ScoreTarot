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
  httpClient: HttpClient = inject(HttpClient);
  router: Router = inject(Router);

  onNewGameClickHandler() {
    this.httpClient.post('http://localhost:8080/game/new', undefined)
      .pipe(catchError(() => of(void 0)))
      .subscribe(
        () => {
          this.router.navigate(['fdf', 'rounds']).then();
        }
      )
  }
}
