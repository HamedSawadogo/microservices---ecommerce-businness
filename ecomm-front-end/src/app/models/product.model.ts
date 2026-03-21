// ===== MODELS =====

export interface BigDecimal {
  // représente un number en pratique
}

export interface Money {
  amount?: number;
}

export interface Event {
  timestamp?: number;
}

export interface Category {
  id?: number;
  name?: string;
  description?: string;
}

export interface Image {
  id?: number;
  imageUrl?: string;
  pathUrl?: string;
}

export interface Tag {
  id?: number;
  name?: string;
  description?: string;
}

export type ProductStatus = 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';
export type PaymentMethod = 'VISA' | 'PAY_PAL';

export interface Product {
  id?: number;
  name?: string;
  price?: Money;
  availableQuantity?: number;
  description?: string;
  domainEvents?: Event[];
  isDeleted?: boolean;
  createdAt?: string;
  status?: ProductStatus;
  category?: Category;
  images?: Image[];
  tags?: Tag[];
}

export interface GetProductPreview {}

export interface CreateOrderItemRequest {
  productId?: number;
  quantity?: number;
}

export interface OrderPaymentRequest {
  amount?: Money;
  paymentMethod?: PaymentMethod;
  idemPotencyKey?: string;
  orderId?: number;
}

export interface Payment {
  id?: number;
  orderId?: number;
  payAt?: object;
  amount?: Money;
  idempotencyKey?: string;
  userId?: number;
  paymentMethod?: PaymentMethod;
}
