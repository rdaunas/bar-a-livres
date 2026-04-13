import {Component, inject, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FetchService} from './service/fetch-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('bar-a-livre-front');

  public connexion() {

  }
}
