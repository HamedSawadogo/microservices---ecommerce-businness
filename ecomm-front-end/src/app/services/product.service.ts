import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product, ProductStatus, GetProductPreview } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly BASE_URL = 'http://localhost:9090/api/v1/products';

  constructor(private http: HttpClient) {}

  // GET /api/v1/products
  findAll(): Observable<any> {
    return this.http.get<any>(this.BASE_URL);
  }

  // GET /api/v1/products/{id}
  findById(id: number): Observable<GetProductPreview> {
    return this.http.get<GetProductPreview>(`${this.BASE_URL}/${id}`);
  }

  // POST /api/v1/products
  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.BASE_URL, product);
  }

  // PUT /api/v1/products/{id}?status=...
  changeProductStatus(id: number, status: ProductStatus): Observable<ProductStatus> {
    const params = new HttpParams().set('status', status);
    return this.http.put<ProductStatus>(`${this.BASE_URL}/${id}`, null, { params });
  }

  // GET /api/v1/products/search?name=...
  search(name: string): Observable<any> {
    const params = new HttpParams().set('name', name);
    return this.http.get<any>(`${this.BASE_URL}/search`, { params });
  }
}
