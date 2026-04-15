import { Injectable, computed, inject } from '@angular/core';

import { NavItem } from '../navigation/nav-item.model';
import {AuthService} from '../service/auth-service';

@Injectable({ providedIn: 'root' })
export class NavigationService {
  private readonly auth = inject(AuthService);

  private readonly items: NavItem[] = [
    { label: 'Catalogue',        route: '/catalogue',      icon: 'menu_book',     roles: ['public', 'user', 'LIBRARIAN', 'admin'] },
    { label: 'Recherche',        route: '/recherche',      icon: 'search',        roles: ['user', 'LIBRARIAN', 'admin'] },
    { label: 'Mon profil',       route: '/profil',         icon: 'assignment',    roles: ['user', 'LIBRARIAN', 'admin'] },
    { label: 'Emprunts',         route: '/emprunts',       icon: 'add_circle',    roles: ['public', 'user', 'LIBRARIAN', 'admin'] },
    { label: 'Ajouter un livre', route: '/livres/ajouter', icon: 'add_circle',    roles: ['LIBRARIAN', 'admin'] },
    { label: 'Statistiques',     route: '/statistiques',   icon: 'bar_chart',     roles: ['LIBRARIAN', 'admin'] },
    { label: 'Utilisateurs',     route: '/utilisateurs',   icon: 'group',         roles: ['admin'] },
  ];

  readonly visibleItems = computed(() =>
    this.items.filter(item => item.roles.includes(this.auth.role()))
  );
}
