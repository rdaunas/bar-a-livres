import { UserRole } from '../auth/user-role.model';

export interface NavItem {
  label: string;
  route: string;
  icon?: string;
  roles: UserRole[];
}
