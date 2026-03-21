import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateOrderItemRequest } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly BASE_URL = 'http://localhost:9090/api/v1/orders';

  constructor(private http: HttpClient) {}

  // GET /orders
  findAll(): Observable<any[]> {
    return this.http.get<any[]>(this.BASE_URL);
  }

  // POST /orders
  addItem(request: CreateOrderItemRequest): Observable<any> {
    return this.http.post<any>(this.BASE_URL, request);
  }
}
