import {Component, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from '../../services/product.service';
import { Product, ProductStatus } from '../../models/product.model';
import {finalize} from 'rxjs';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [ProductService],
  template: `
    <div class="form-container">
      <h2>➕ Créer un produit</h2>
      <div *ngIf="success" class="alert success">✅ Produit créé avec succès !</div>
      <div *ngIf="error" class="alert error">❌ {{ error }}</div>
      <form (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>Nom *</label>
          <input [(ngModel)]="product.name" name="name" required placeholder="Nom du produit" />
        </div>
        <div class="form-group">
          <label>Description</label>
          <textarea [(ngModel)]="product.description" name="description" rows="3" placeholder="Description..."></textarea>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Prix (€)</label>
            <input
              type="number"
              [(ngModel)]="priceAmount"
              name="priceAmount"
              min="0"
              step="0.01"
              placeholder="0.00"
            />
          </div>
          <div class="form-group">
            <label>Quantité disponible</label>
            <input
              type="number"
              [(ngModel)]="product.availableQuantity"
              name="availableQuantity"
              min="0"
              placeholder="0"
            />
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" [disabled]="loading()" class="btn-submit">
            {{ loading() ? 'Création...' : 'Créer le produit' }}
          </button>
          <button type="button" (click)="reset()" class="btn-reset">Réinitialiser</button>
        </div>
      </form>
      <div *ngIf="createdProduct" class="created-preview">
        <h3>Produit créé :</h3>
        <pre>{{ createdProduct | json }}</pre>
      </div>
    </div>
  `,
  styles: [`
    .form-container {
      max-width: 600px;
      margin: 0 auto;
      padding: 32px 24px;
      background: white;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.08);
      font-family: 'Segoe UI', sans-serif;
    }
    h2 { margin: 0 0 24px; font-size: 22px; }
    .form-group { margin-bottom: 16px; display: flex; flex-direction: column; gap: 6px; }
    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
    label { font-size: 13px; font-weight: 600; color: #444; }
    input, textarea, select {
      padding: 10px 12px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 14px;
      outline: none;
      transition: border-color 0.2s;
      font-family: inherit;
    }
    input:focus, textarea:focus, select:focus { border-color: #6c63ff; }
    .form-actions { display: flex; gap: 12px; margin-top: 20px; }
    .btn-submit {
      flex: 1;
      padding: 12px;
      background: #6c63ff;
      color: white;
      border: none;
      border-radius: 8px;
      font-size: 15px;
      font-weight: 700;
      cursor: pointer;
      transition: background 0.2s;
    }
    .btn-submit:hover:not(:disabled) { background: #5b52e0; }
    .btn-submit:disabled { opacity: 0.6; cursor: not-allowed; }
    .btn-reset {
      padding: 12px 20px;
      background: white;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 15px;
      cursor: pointer;
      transition: border-color 0.2s;
    }
    .btn-reset:hover { border-color: #6c63ff; }
    .alert { padding: 10px 16px; border-radius: 8px; margin-bottom: 16px; font-size: 14px; }
    .alert.success { background: #d1fae5; color: #065f46; }
    .alert.error { background: #fee2e2; color: #b91c1c; }
    .created-preview {
      margin-top: 24px;
      padding: 16px;
      background: #f5f3ff;
      border-radius: 8px;
    }
    .created-preview h3 { margin: 0 0 8px; font-size: 15px; }
    pre { font-size: 12px; overflow: auto; margin: 0; }
  `]
})
export class ProductFormComponent {
  product: Product = { status: 'DRAFT', availableQuantity: 0 };
  priceAmount: number = 0;
  categoryName = '';
  statusOptions: ProductStatus[] = ['DRAFT', 'PUBLISHED', 'ARCHIVED'];
  loading = signal(false);
  success = false;
  error = '';
  createdProduct: Product | null = null;

  constructor(private productService: ProductService) {}

  onSubmit(): void {
    this.loading.set(true);
    this.success = false;
    this.error = '';

    const payload: Product = {
      ...this.product,
      price: { amount: this.priceAmount },
      category: this.categoryName ? { name: this.categoryName } : undefined
    };

    this.productService.createProduct(payload)
      .pipe(
        finalize(() => this.loading.set(false))
      )
      .subscribe({
      next: (created) => {
        this.createdProduct = created;
        this.success = true;
        this.reset();
      },
      error: (err) => {
        this.error = 'Erreur lors de la création du produit.';
      }
    });
  }

  reset(): void {
    this.product = { status: 'DRAFT', availableQuantity: 0 };
    this.priceAmount = 0;
    this.categoryName = '';
  }
}
