import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductListComponent} from './components/products-list/products-list';
import {OrderListComponent} from './components/orders/orders';
import {PaymentFormComponent} from './components/payment-form/payment-form';
import {ProductFormComponent} from './components/product-form/product-form';
import {RouterLink} from '@angular/router';
type Tab = 'products' | 'create' | 'orders' | 'payment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    ProductListComponent,
    ProductFormComponent,
    OrderListComponent,
    PaymentFormComponent,
    RouterLink
  ],
  template: `
    <div class="app-shell">
      <nav class="navbar">
        <h1 style="cursor: pointer" class="brand" [routerLink]="'/'" >🛍️ ShopAdmin</h1>
        <div class="nav-tabs">
          <button
            *ngFor="let tab of tabs"
            [class.active]="activeTab === tab.key"
            (click)="activeTab = tab.key"
          >
            {{ tab.icon }} {{ tab.label }}
          </button>
        </div>
      </nav>

      <main class="main-content">
        <app-product-list *ngIf="activeTab === 'products'" />
        <app-product-form *ngIf="activeTab === 'create'" />
        <app-order-list *ngIf="activeTab === 'orders'" />
        <app-payment-form *ngIf="activeTab === 'payment'" />
      </main>
    </div>
  `,
  styles: [`
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { background: #f4f5f7; }
    .app-shell { min-height: 100vh; background: #f4f5f7; }
    .navbar {
      background: white;
      padding: 0 32px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      box-shadow: 0 2px 8px rgba(0,0,0,0.07);
      height: 60px;
      position: sticky;
      top: 0;
      z-index: 100;
    }
    .brand { font-size: 20px; font-weight: 800; color: #6c63ff; font-family: 'Segoe UI', sans-serif; }
    .nav-tabs { display: flex; gap: 4px; }
    .nav-tabs button {
      padding: 8px 16px;
      background: none;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      color: #666;
      transition: all 0.2s;
      font-family: 'Segoe UI', sans-serif;
    }
    .nav-tabs button:hover { background: #f5f3ff; color: #6c63ff; }
    .nav-tabs button.active { background: #ede9fe; color: #6c63ff; font-weight: 700; }
    .main-content { padding: 32px 24px; }
  `]
})
export class AppComponent {
  activeTab: Tab = 'products';
  tabs: { key: Tab; label: string; icon: string }[] = [
    { key: 'create', label: 'Nouveau produit', icon: '➕' },
    { key: 'orders', label: 'Mon pannier', icon: '📦' },
  ];
}
