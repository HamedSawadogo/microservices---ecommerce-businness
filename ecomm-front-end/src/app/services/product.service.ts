import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {Product, Page, ProductStatus, ProductForm, OrderItemRequest} from "../models/product.model";

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly BASE = 'http://localhost:9090/api/v1/products';

  getAll(page = 0, size = 10): Observable<Page<Product>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<Page<Product>>(this.BASE, { params });
  }

  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.BASE}/${id}`);
  }

  search(name: string, page = 0, size = 10): Observable<Page<Product>> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Page<Product>>(`${this.BASE}/search`, { params });
  }

  create(product: ProductForm): Observable<Product> {
    return this.http.post<Product>(this.BASE, product);
  }

  updateStatus(id: number, status: ProductStatus): Observable<ProductStatus> {
    const params = new HttpParams().set('status', status);
    return this.http.put<ProductStatus>(`${this.BASE}/${id}`, null, { params });
  }

  orderProduct(req: OrderItemRequest): Observable<any> {
    return this.http.post<any>(`http://localhost:9090/orders`, req);
  }
}
