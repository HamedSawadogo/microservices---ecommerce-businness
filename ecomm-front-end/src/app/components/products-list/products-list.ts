import {Component, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from '../../services/product.service';
import { OrderService } from '../../services/order.service';
import {Product, ProductStatus} from '../../models/product.model';
import {finalize} from 'rxjs';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [ProductService, OrderService],
  template: `
    <div class="product-list-container">
      <div class="header">
        <h1>🛒 Catalogue Produits</h1>

        <div class="search-bar">
          <input
            type="text"
            [(ngModel)]="searchQuery"
            placeholder="Rechercher un produit..."
            (input)="onSearch()"
          />
        </div>
      </div>

      @if (loading()) {
        <div class="loading">Chargement...</div>
      }

      @if (error) {
        <div class="error">{{ error }}</div>
      }

      @if (!loading()) {
        <div class="products-grid">

          @for (product of products; track product.id) {
            <div class="product-card">

              <div class="product-badge" [ngClass]="product.status?.toLowerCase()">
                {{ product.status }}
              </div>

              <div class="product-image">

                @if (product.images && product.images.length > 0) {
                  <img
                    [src]="product.images[0].imageUrl"
                    [alt]="product.name"
                  />
                } @else {
                  <div class="no-image">📦</div>
                }

              </div>

              <div class="product-info">
                <h3>{{ product.name }}</h3>
                <p class="description">{{ product.description }}</p>

                <div class="meta">
              <span class="price">
                {{ product.price?.amount | currency:'EUR' }}
              </span>
                  <span class="qty">
                Stock: {{ product.availableQuantity }}
              </span>
                </div>

                @if (product.category) {
                  <div class="category">
                    🏷️ {{ product.category.name }}
                  </div>
                }

                @if (product.tags && product.tags.length > 0) {
                  <div class="tags">
                    @for (tag of product.tags; track tag.id) {
                      <span class="tag">{{ tag.name }}</span>
                    }
                  </div>
                }

              </div>

              <div class="product-actions">
                <button class="btn-add-order" (click)="addToOrder(product)">
                  + Ajouter au panier
                </button>
              </div>

            </div>
          }

        </div>
      }

      @if (toastMessage) {
        <div class="success-toast">
          {{ toastMessage }}
        </div>
      }

    </div>
  `,
  styles: [`
    .product-list-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 24px;
      font-family: 'Segoe UI', sans-serif;
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 24px;
    }
    .header h1 { margin: 0; font-size: 28px; }
    .search-bar input {
      padding: 10px 16px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 15px;
      width: 280px;
      outline: none;
      transition: border-color 0.2s;
    }
    .search-bar input:focus { border-color: #6c63ff; }
    .status-filter {
      display: flex;
      gap: 8px;
      margin-bottom: 20px;
    }
    .status-filter button {
      padding: 6px 14px;
      border: 2px solid #d0d0d0;
      border-radius: 20px;
      background: white;
      cursor: pointer;
      font-size: 13px;
      transition: all 0.2s;
    }
    .status-filter button.active {
      background: #6c63ff;
      border-color: #6c63ff;
      color: white;
    }
    .products-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 20px;
    }
    .product-card {
      background: white;
      border-radius: 12px;
      border: 1px solid #ececec;
      padding: 16px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.06);
      position: relative;
      transition: transform 0.2s, box-shadow 0.2s;
    }
    .product-card:hover {
      transform: translateY(-3px);
      box-shadow: 0 6px 20px rgba(0,0,0,0.1);
    }
    .product-badge {
      position: absolute;
      top: 12px;
      right: 12px;
      font-size: 11px;
      font-weight: 700;
      padding: 3px 8px;
      border-radius: 10px;
      text-transform: uppercase;
    }
    .product-badge.draft { background: #fef3c7; color: #92400e; }
    .product-badge.published { background: #d1fae5; color: #065f46; }
    .product-badge.archived { background: #f3f4f6; color: #6b7280; }
    .product-image {
      height: 120px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f9f9f9;
      border-radius: 8px;
      margin-bottom: 12px;
    }
    .product-image img { max-height: 100%; max-width: 100%; object-fit: contain; border-radius: 8px; }
    .no-image { font-size: 48px; }
    .product-info h3 { margin: 0 0 6px; font-size: 16px; font-weight: 600; }
    .description { font-size: 13px; color: #666; margin: 0 0 10px; min-height: 36px; }
    .meta { display: flex; justify-content: space-between; margin-bottom: 6px; }
    .price { font-weight: 700; font-size: 16px; color: #6c63ff; }
    .qty { font-size: 13px; color: #888; }
    .category { font-size: 12px; color: #555; margin-bottom: 8px; }
    .tags { display: flex; flex-wrap: wrap; gap: 4px; margin-bottom: 10px; }
    .tag { background: #ede9fe; color: #5b21b6; padding: 2px 8px; border-radius: 10px; font-size: 11px; }
    .product-actions { display: flex; gap: 8px; flex-direction: column; }
    .btn-add-order {
      padding: 8px;
      background: #6c63ff;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 600;
      fontx-size: 14px;
      transition: background 0.2s;
    }
    .btn-add-order:hover { background: #5b52e0; }
    .product-actions select {
      padding: 6px;
      border: 1px solid #d0d0d0;
      border-radius: 8px;
      font-size: 13px;
      background: white;
    }
    .loading, .empty { text-align: center; padding: 48px; color: #888; font-size: 18px; }
    .error { background: #fee2e2; color: #b91c1c; padding: 12px 16px; border-radius: 8px; margin-bottom: 16px; }
    .success-toast {
      position: fixed;
      bottom: 24px;
      right: 24px;
      background: #6c63ff;
      color: white;
      padding: 12px 24px;
      border-radius: 10px;
      box-shadow: 0 4px 16px rgba(108,99,255,0.35);
      font-weight: 600;
      z-index: 999;
      animation: fadeIn 0.3s;
    }
    @keyframes fadeIn { from { opacity:0; transform: translateY(10px); } to { opacity:1; transform: none; } }
  `]
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  searchQuery: null | string  = null;
  loading = signal(false);
  error = '';
  toastMessage = '';

  constructor(
    private productService: ProductService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.loading.set(true);
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.findAll()
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (data) => {
          this.products = data.content;
        },
      error: () => {
        this.error = 'Erreur lors du chargement des produits.';
      }
    });
  }

  onSearch(): void {
    if (this.searchQuery != null && this.searchQuery.trim().length > 0) {
      this.productService.search(this.searchQuery!).subscribe({
        next: (data) => {
          this.products = data.content;
        },
        error: () => { this.loadProducts(); }
      });
    } else if (this.searchQuery?.trim().length === 0) {
      this.loadProducts();
    }
  }

  addToOrder(product: Product): void {
    if (!product.id) return;
    this.orderService.addItem({ productId: product.id, quantity: 1 }).subscribe({
      next: () => this.showToast(`"${product.name}" ajouté au panier !`),
      error: () => this.showToast('Erreur lors de l\'ajout.')
    });
  }

  showToast(message: string): void {
    this.toastMessage = message;
    setTimeout(() => (this.toastMessage = ''), 3000);
  }
}
