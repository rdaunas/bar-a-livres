import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

import { AuthService } from '../../core/services/auth-service';
import { NavigationService } from '../../core/services/navigation.service';

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
  readonly navigation = inject(NavigationService);
  private readonly breakpointObserver = inject(BreakpointObserver);

  readonly isMobile = computed(() =>
    this.breakpointObserver.isMatched(Breakpoints.Handset)
  );

  goBack(): void {
    window.history.back();
  }

  logout(): void {
    this.auth.logout();
  }
}
