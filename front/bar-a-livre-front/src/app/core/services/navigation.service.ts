import { Injectable, computed, inject } from '@angular/core';
import { AuthService } from './auth-service';
import { NavItem } from '../navigation/nav-item.model';

@Injectable({ providedIn: 'root' })
export class NavigationService {
  private readonly auth = inject(AuthService);

  private readonly items: NavItem[] = [
    { label: 'Catalogue',        route: '/catalogue',      icon: 'menu_book',     roles: ['public', 'user', 'librarian', 'admin'] },
    { label: 'Recherche',        route: '/recherche',      icon: 'search',        roles: ['user', 'librarian', 'admin'] },
    { label: 'Mon profil',       route: '/profil',         icon: 'assignment',    roles: ['user', 'librarian', 'admin'] },
    { label: 'Emprunts',         route: '/emprunts',       icon: 'add_circle',    roles: ['librarian', 'admin'] },
    { label: 'Ajouter un livre', route: '/livres/ajouter', icon: 'add_circle',    roles: ['librarian', 'admin'] },
    { label: 'Statistiques',     route: '/statistiques',   icon: 'bar_chart',     roles: ['librarian', 'admin'] },
    { label: 'Utilisateurs',     route: '/utilisateurs',   icon: 'group',         roles: ['admin'] },
  ];

  readonly visibleItems = computed(() =>
    this.items.filter(item => item.roles.includes(this.auth.role()))
  );
}
