export interface LivreDTO {
  isbn: string;
  titre: string;
  auteur: string;
  description: string;
  couverture: string;
  nbExemplaires: number;
  dateAjout: string;
  isActive: boolean;
  categories: string[];
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}
