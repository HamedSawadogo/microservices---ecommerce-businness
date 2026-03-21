import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {ApiService} from '../../services/service';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h2>Payment</h2>

    <input [(ngModel)]="payment.orderId" placeholder="Order ID" type="number"/>
    <input [(ngModel)]="payment.amount.amount" placeholder="Amount" type="number"/>

    <select [(ngModel)]="payment.paymentMethod">
      <option value="VISA">VISA</option>
      <option value="PAY_PAL">PAY_PAL</option>
    </select>

    <button (click)="pay()">Pay</button>
  `
})
export class PaymentComponent {

  payment: any = {
    orderId: null,
    amount: { amount: 0 },
    paymentMethod: 'VISA'
  };

  constructor(private api: ApiService) {}

  pay() {
    const key = crypto.randomUUID(); // idempotency

    this.api.makePayment(this.payment, key)
      .subscribe(res => console.log('Payment success', res));
  }
}
