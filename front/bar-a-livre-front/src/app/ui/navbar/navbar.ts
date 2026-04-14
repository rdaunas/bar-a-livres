import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AuthService } from '../../core/services/auth.service';

export interface NavItem {
  label: string;
  route: string;
  icon?: string;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule,
  ],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar {

  readonly auth = inject(AuthService);
  private readonly breakpointObserver = inject(BreakpointObserver);

  readonly isMobile = computed(() => {
    return window.innerWidth <= 768;
  });

  readonly navItems = computed((): NavItem[] => {
    switch (this.auth.role()) {
      case 'adherent':
        return [
          { label: 'Consulter le catalogue', route: '/catalogue', icon: 'menu_book' },
        ];
      case 'bibliothecaire':
        return [
          { label: 'Gestion des emprunts', route: '/emprunts', icon: 'assignment' },
          { label: 'Consulter le catalogue', route: '/catalogue', icon: 'menu_book' },
          { label: 'Ajouter un livre', route: '/livres/ajouter', icon: 'add_circle' },
          { label: 'Statistiques', route: '/statistiques', icon: 'bar_chart' },
        ];
      case 'admin':
        return [
          { label: 'Liste des utilisateurs', route: '/utilisateurs', icon: 'group' },
          { label: 'Statistiques', route: '/statistiques', icon: 'bar_chart' },
        ];
      default: // 'user' non connecté
        return [];
    }
  });

  goBack(): void {
    window.history.back();
  }

  logout(): void {
    this.auth.logout();
  }
}

