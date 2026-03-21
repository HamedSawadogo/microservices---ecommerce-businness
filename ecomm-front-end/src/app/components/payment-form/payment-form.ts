import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PaymentService } from '../../services/payment.service';
import { OrderPaymentRequest, Payment, PaymentMethod } from '../../models/product.model';

@Component({
  selector: 'app-payment-form',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [PaymentService],
  template: `
    <div class="payment-container">
      <h2>💳 Paiement</h2>

      <div *ngIf="success" class="alert success">✅ Paiement effectué avec succès !</div>
      <div *ngIf="error" class="alert error">❌ {{ error }}</div>

      <form (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>ID Commande *</label>
          <input
            type="number"
            [(ngModel)]="request.orderId"
            name="orderId"
            required
            placeholder="ID de la commande"
          />
        </div>

        <div class="form-group">
          <label>Montant (€) *</label>
          <input
            type="number"
            [(ngModel)]="amount"
            name="amount"
            min="0"
            step="0.01"
            required
            placeholder="0.00"
          />
        </div>

        <div class="form-group">
          <label>Méthode de paiement *</label>
          <div class="payment-methods">
            <div
              class="method-card"
              [class.selected]="request.paymentMethod === 'VISA'"
              (click)="request.paymentMethod = 'VISA'"
            >
              💳 VISA
            </div>
            <div
              class="method-card"
              [class.selected]="request.paymentMethod === 'PAY_PAL'"
              (click)="request.paymentMethod = 'PAY_PAL'"
            >
              🅿️ PayPal
            </div>
          </div>
        </div>

        <div class="form-group idempotency">
          <label>Clé d'idempotence</label>
          <div class="key-row">
            <input
              [(ngModel)]="idempotencyKey"
              name="idempotencyKey"
              readonly
              placeholder="Générée automatiquement"
            />
            <button type="button" (click)="generateKey()" class="btn-gen">🔄 Générer</button>
          </div>
          <small>Cette clé garantit qu'un paiement ne sera pas dupliqué.</small>
        </div>

        <button type="submit" [disabled]="loading || !request.paymentMethod" class="btn-pay">
          {{ loading ? 'Traitement...' : 'Payer maintenant' }}
        </button>
      </form>

      <div *ngIf="payment" class="payment-result">
        <h3>Reçu de paiement</h3>
        <div class="receipt">
          <div class="receipt-row"><span>ID Paiement</span><strong>{{ payment.id }}</strong></div>
          <div class="receipt-row"><span>Commande</span><strong>#{{ payment.orderId }}</strong></div>
          <div class="receipt-row"><span>Montant</span><strong>{{ payment.amount?.amount | currency:'EUR' }}</strong></div>
          <div class="receipt-row"><span>Méthode</span><strong>{{ payment.paymentMethod }}</strong></div>
          <div class="receipt-row"><span>Utilisateur</span><strong>{{ payment.userId }}</strong></div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .payment-container {
      max-width: 520px;
      margin: 0 auto;
      padding: 32px 24px;
      background: white;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.08);
      font-family: 'Segoe UI', sans-serif;
    }
    h2 { margin: 0 0 24px; font-size: 22px; }
    .form-group { margin-bottom: 18px; display: flex; flex-direction: column; gap: 6px; }
    label { font-size: 13px; font-weight: 600; color: #444; }
    input {
      padding: 10px 12px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 14px;
      outline: none;
      transition: border-color 0.2s;
    }
    input:focus { border-color: #6c63ff; }
    input[readonly] { background: #f9f9f9; color: #666; }
    .payment-methods { display: flex; gap: 12px; }
    .method-card {
      flex: 1;
      border: 2px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-align: center;
      cursor: pointer;
      font-size: 15px;
      font-weight: 600;
      transition: all 0.2s;
      user-select: none;
    }
    .method-card:hover { border-color: #a5b4fc; background: #f5f3ff; }
    .method-card.selected { border-color: #6c63ff; background: #ede9fe; color: #4c1d95; }
    .key-row { display: flex; gap: 8px; }
    .key-row input { flex: 1; font-size: 12px; }
    .btn-gen {
      padding: 10px 14px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      background: white;
      cursor: pointer;
      font-size: 13px;
      white-space: nowrap;
    }
    .btn-gen:hover { border-color: #6c63ff; }
    small { font-size: 11px; color: #888; }
    .btn-pay {
      width: 100%;
      padding: 14px;
      background: linear-gradient(135deg, #6c63ff, #8b5cf6);
      color: white;
      border: none;
      border-radius: 10px;
      font-size: 16px;
      font-weight: 700;
      cursor: pointer;
      transition: opacity 0.2s;
      margin-top: 8px;
    }
    .btn-pay:hover:not(:disabled) { opacity: 0.9; }
    .btn-pay:disabled { opacity: 0.5; cursor: not-allowed; }
    .alert { padding: 10px 16px; border-radius: 8px; margin-bottom: 16px; font-size: 14px; }
    .alert.success { background: #d1fae5; color: #065f46; }
    .alert.error { background: #fee2e2; color: #b91c1c; }
    .payment-result {
      margin-top: 24px;
      background: #f5f3ff;
      border-radius: 12px;
      padding: 18px;
    }
    .payment-result h3 { margin: 0 0 12px; font-size: 16px; color: #5b21b6; }
    .receipt { display: flex; flex-direction: column; gap: 8px; }
    .receipt-row { display: flex; justify-content: space-between; font-size: 14px; }
    .receipt-row span { color: #666; }
  `]
})
export class PaymentFormComponent {
  request: OrderPaymentRequest = { paymentMethod: undefined };
  amount = 0;
  idempotencyKey: string = this.newKey();
  loading = false;
  success = false;
  error = '';
  payment: Payment | null = null;

  constructor(private paymentService: PaymentService) {}

  generateKey(): void {
    this.idempotencyKey = this.newKey();
  }

  private newKey(): string {
    // Génération simple si uuid non disponible
    return 'idem-' + Date.now() + '-' + Math.random().toString(36).substring(2, 9);
  }

  onSubmit(): void {
    if (!this.request.orderId || !this.request.paymentMethod) return;
    this.loading = true;
    this.success = false;
    this.error = '';
    this.payment = null;

    const payload: OrderPaymentRequest = {
      ...this.request,
      amount: { amount: this.amount },
      idemPotencyKey: this.idempotencyKey
    };

    this.paymentService.makePayment(this.idempotencyKey, payload).subscribe({
      next: (result) => {
        this.payment = result;
        this.success = true;
        this.loading = false;
        this.generateKey(); // rotation de la clé après paiement réussi
      },
      error: () => {
        this.error = 'Erreur lors du paiement. Veuillez réessayer.';
        this.loading = false;
      }
    });
  }
}
