import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { OrderService } from '../../services/order.service';
import { CreateOrderItemRequest } from '../../models/product.model';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [OrderService],
  template: `
    <div class="order-container">
      <h2>📦 Commandes</h2>

      <div class="add-item-form">
        <h3>Ajouter un article</h3>
        <div class="inline-form">
          <input
            type="number"
            [(ngModel)]="newItem.productId"
            placeholder="ID produit"
            min="1"
          />
          <input
            type="number"
            [(ngModel)]="newItem.quantity"
            placeholder="Quantité"
            min="1"
          />
          <button (click)="addItem()" [disabled]="loading" class="btn-add">
            {{ loading ? '...' : '+ Ajouter' }}
          </button>
        </div>
        <div *ngIf="addSuccess" class="alert success">✅ Article ajouté !</div>
        <div *ngIf="addError" class="alert error">❌ {{ addError }}</div>
      </div>

      <div class="orders-section">
        <div class="section-header">
          <h3>Liste des commandes</h3>
          <button (click)="loadOrders()" class="btn-refresh">🔄 Rafraîchir</button>
        </div>

        <div *ngIf="loadError" class="alert error">{{ loadError }}</div>
        <div *ngIf="loadingList" class="loading">Chargement...</div>

        <div *ngIf="!loadingList && orders.length === 0" class="empty">
          Aucune commande trouvée.
        </div>

        <div class="orders-list" *ngIf="!loadingList && orders.length > 0">
          <div class="order-card" *ngFor="let order of orders; let i = index">
            <div class="order-number">#{{ i + 1 }}</div>
            <pre>{{ order | json }}</pre>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .order-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 32px 24px;
      font-family: 'Segoe UI', sans-serif;
    }
    h2 { font-size: 24px; margin: 0 0 24px; }
    h3 { font-size: 17px; margin: 0 0 14px; }
    .add-item-form {
      background: white;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.07);
      margin-bottom: 24px;
    }
    .inline-form { display: flex; gap: 10px; align-items: center; }
    .inline-form input {
      padding: 10px 12px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 14px;
      width: 130px;
      outline: none;
    }
    .inline-form input:focus { border-color: #6c63ff; }
    .btn-add {
      padding: 10px 20px;
      background: #6c63ff;
      color: white;
      border: none;
      border-radius: 8px;
      font-weight: 700;
      cursor: pointer;
      font-size: 14px;
      transition: background 0.2s;
    }
    .btn-add:hover:not(:disabled) { background: #5b52e0; }
    .btn-add:disabled { opacity: 0.6; cursor: not-allowed; }
    .alert { padding: 10px 16px; border-radius: 8px; margin-top: 12px; font-size: 14px; }
    .alert.success { background: #d1fae5; color: #065f46; }
    .alert.error { background: #fee2e2; color: #b91c1c; }
    .orders-section {
      background: white;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.07);
    }
    .section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
    .btn-refresh {
      padding: 7px 14px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      background: white;
      cursor: pointer;
      font-size: 13px;
      transition: border-color 0.2s;
    }
    .btn-refresh:hover { border-color: #6c63ff; }
    .loading, .empty { text-align: center; color: #888; padding: 24px; }
    .order-card {
      border: 1px solid #ececec;
      border-radius: 10px;
      padding: 14px;
      margin-bottom: 12px;
      position: relative;
    }
    .order-number {
      position: absolute;
      top: 10px;
      right: 12px;
      font-weight: 700;
      font-size: 13px;
      color: #6c63ff;
    }
    pre { font-size: 12px; overflow: auto; margin: 0; color: #333; }
  `]
})
export class OrderListComponent implements OnInit {
  orders: any[] = [];
  newItem: CreateOrderItemRequest = { productId: undefined, quantity: 1 };
  loading = false;
  loadingList = false;
  addSuccess = false;
  addError = '';
  loadError = '';

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loadingList = true;
    this.loadError = '';
    this.orderService.findAll().subscribe({
      next: (data) => {
        this.orders = Array.isArray(data) ? data : [data];
        this.loadingList = false;
      },
      error: () => {
        this.loadError = 'Erreur lors du chargement des commandes.';
        this.loadingList = false;
      }
    });
  }

  addItem(): void {
    if (!this.newItem.productId || !this.newItem.quantity) return;
    this.loading = true;
    this.addSuccess = false;
    this.addError = '';
    this.orderService.addItem(this.newItem).subscribe({
      next: () => {
        this.addSuccess = true;
        this.loading = false;
        this.newItem = { productId: undefined, quantity: 1 };
        setTimeout(() => (this.addSuccess = false), 3000);
        this.loadOrders();
      },
      error: () => {
        this.addError = 'Erreur lors de l\'ajout de l\'article.';
        this.loading = false;
      }
    });
  }
}
