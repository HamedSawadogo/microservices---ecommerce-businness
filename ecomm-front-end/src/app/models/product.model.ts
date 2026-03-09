export type ProductStatus = 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';

export interface Product {
  id: number;
  name: string;
  price: number;
  availableQuantity: number;
  description: string;
  status: ProductStatus;
  createdAt: string;
  category?: { id: number; name: string };
  tags?: { id: number; name: string }[];
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface ProductForm {
  name: string;
  price: number | null;
  availableQuantity: number;
  description: string;
  status: ProductStatus;
}
