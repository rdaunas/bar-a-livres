import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service';
import { UserRole } from '../auth/user-role.model';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth    = inject(AuthService);
  const router  = inject(Router);
  const allowed = route.data['roles'] as UserRole[];


  // @ts-ignore
  if (auth.hasRole(...allowed)) return true;

  router.navigate(['/403']);
  return false;
};
