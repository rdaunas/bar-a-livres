import { UserRole } from './user-role.model';

export interface AuthResponse {
  token: string;
  role: UserRole;
}
