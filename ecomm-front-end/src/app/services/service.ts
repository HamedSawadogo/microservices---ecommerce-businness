import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private baseUrl = 'http://localhost:api/v1/'; // change selon ton backend

  constructor(private http: HttpClient) {}

  // ================= PRODUCTS =================

  getProducts(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/v1/products`);
  }

  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/v1/products/${id}`);
  }

  createProduct(product: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/v1/products`, product);
  }

  changeProductStatus(id: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/api/v1/products/${id}?status=${status}`, {});
  }

  searchProducts(name: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/v1/products/search?name=${name}`);
  }

  // ================= ORDERS =================

  addOrderItem(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/orders`, data);
  }

  getOrders(): Observable<any> {
    return this.http.get(`${this.baseUrl}/orders`);
  }

  // ================= PAYMENTS =================

  makePayment(data: any, idempotencyKey: string): Observable<any> {
    const headers = new HttpHeaders({
      'idempotency-key': idempotencyKey
    });

    return this.http.post(`${this.baseUrl}/payments`, data, { headers });
  }
}
