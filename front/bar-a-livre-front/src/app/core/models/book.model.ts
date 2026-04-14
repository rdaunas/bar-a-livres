export interface LivreDTO {
  isbn: string;
  titre: string;
  auteur: string;
  description: string;
  couverture: string;
  nbExemplaire: number;
  dateAjout: string;
  isActive: boolean;
  categories: { id: number; nom: string }[];
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
