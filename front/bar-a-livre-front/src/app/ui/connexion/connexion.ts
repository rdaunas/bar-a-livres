import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AuthService, Role } from '../../core/services/auth.service';

/**
 * Exemple de page de connexion simulée.
 * Remplacer par un vrai appel API + JWT dans un projet réel.
 */
@Component({
  selector: 'app-connexion',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  template: `
    <div style="padding: 2rem; display: flex; flex-direction: column; gap: 1rem; max-width: 400px; margin: 4rem auto;">
      <h2>Connexion (démo)</h2>
      <p style="color: #666; font-size: 0.9rem;">Cliquez sur un rôle pour simuler la connexion.</p>

      @for (r of roles; track r.role) {
        <button mat-raised-button [color]="r.color" (click)="login(r.role, r.pseudo)">
          Se connecter en tant que {{ r.label }}
        </button>
      }

      @if (auth.isLoggedIn()) {
        <button mat-stroked-button color="warn" (click)="auth.logout()">
          Déconnexion ({{ auth.pseudo() }})
        </button>
      }
    </div>
  `
})
export class Connexion {
  readonly auth = inject(AuthService);

  roles = [
    { role: 'user' as Role,          pseudo: '',              label: 'Utilisateur',      color: '' },
    { role: 'adherent' as Role,      pseudo: 'Jean Dupont',   label: 'Adhérent',         color: 'primary' },
    { role: 'bibliothecaire' as Role,pseudo: 'Marie Curie',   label: 'Bibliothécaire',   color: 'accent' },
    { role: 'admin' as Role,         pseudo: 'Admin',         label: 'Administrateur',   color: 'warn' },
  ];

  login(role: Role, pseudo: string): void {
    this.auth.login({ role, pseudo });
  }
}

