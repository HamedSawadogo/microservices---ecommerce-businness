import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderPaymentRequest, Payment } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly BASE_URL = 'http://localhost:9090/api/v1/payments';

  constructor(private http: HttpClient) {}

  // POST /payments (avec header idempotency-key)
  makePayment(idempotencyKey: string, request: OrderPaymentRequest): Observable<Payment> {
    const headers = new HttpHeaders({ 'idempotency-key': idempotencyKey });
    return this.http.post<Payment>(this.BASE_URL, request, { headers });
  }
}
