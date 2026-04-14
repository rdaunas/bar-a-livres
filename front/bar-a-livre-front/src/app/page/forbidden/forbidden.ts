import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-forbidden',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="forbidden-wrapper">
      <h1>403</h1>
      <p>Vous n'avez pas les droits nécessaires pour accéder à cette page.</p>
      <a routerLink="/catalogue">Retour au catalogue</a>
    </div>
  `,
  styles: [`
    .forbidden-wrapper {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 60vh;
      gap: 1rem;
      text-align: center;
    }
    h1 { font-size: 6rem; font-weight: 200; opacity: .3; margin: 0; }
    p  { color: var(--color-text-secondary, #555); }
    a  { color: var(--color-primary, #333); }
  `],
})
export class Forbidden {}
