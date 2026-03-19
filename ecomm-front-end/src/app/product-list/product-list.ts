import {
  Component, OnInit, OnDestroy, inject, signal,
  computed, ChangeDetectionStrategy
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {
  Subject, debounceTime, distinctUntilChanged,
  switchMap, catchError, of, takeUntil
} from 'rxjs';

import { ProductService } from '../services/product.service';
import {Product, Page, ProductStatus, ProductForm, OrderItemRequest} from '../models/product.model';

type SortField = 'id' | 'name' | 'price' | 'availableQuantity' | 'status';
type FilterStatus = 'ALL' | ProductStatus;

interface Toast { message: string; type: 'success' | 'error'; id: number }

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: 'product-list.html',
  styleUrls: ['product-list.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [ProductService],
})
export class ProductsComponent implements OnInit, OnDestroy {

  private readonly svc = inject(ProductService);
  private readonly destroy$ = new Subject<void>();
  private readonly search$ = new Subject<string>();
  private toastCounter = 0;

  // ── State ──────────────────────────────────────────────────────────────
  products = signal<Product[]>([]);
  totalElements = signal(0);
  totalPages = signal(0);
  loading = signal(true);
  page = signal(0);
  pageSize = 10;

  searchQuery = signal('');
  activeFilter = signal<FilterStatus>('ALL');
  sortField = signal<SortField>('id');
  sortDir = signal<1 | -1>(1);

  showModal = signal(false);
  editingProduct = signal<Product | null>(null);
  toasts = signal<Toast[]>([]);

  form: ProductForm = this.emptyForm();

  // ── Computed ────────────────────────────────────────────────────────────
  filteredProducts = computed(() => {
    let list = this.products();
    if (this.activeFilter() !== 'ALL') {
      list = list.filter(p => p.status === this.activeFilter());
    }
    const field = this.sortField();
    const dir = this.sortDir();
    return [...list].sort((a, b) => {
      const va = a[field] as string | number;
      const vb = b[field] as string | number;
      if (typeof va === 'string') return va.localeCompare(vb as string) * dir;
      return ((va as number) - (vb as number)) * dir;
    });
  });

  stats = computed(() => {
    const all = this.products();
    return {
      total: this.totalElements(),
      published: all.filter(p => p.status === 'PUBLISHED').length,
      draft: all.filter(p => p.status === 'DRAFT').length,
      archived: all.filter(p => p.status === 'ARCHIVED').length,
    };
  });

  pages = computed(() => Array.from({ length: this.totalPages() }, (_, i) => i));

  // ── Lifecycle ───────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.loadProducts();

    this.search$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(q => {
        this.page.set(0);
        this.loading.set(true);
        const obs = q.trim()
          ? this.svc.search(q, this.page(), this.pageSize)
          : this.svc.getAll(this.page(), this.pageSize);
        return obs.pipe(catchError(() => of(this.mockPage())));
      }),
      takeUntil(this.destroy$)
    ).subscribe(p => this.applyPage(p));
  }


  onOrder(product: Product) {
    const req: OrderItemRequest = {
      productId: product.id,
      quantity: 1
    }
    this.svc.orderProduct(req).subscribe({
      next: (val) => {
        console.log("value: " + val);
      }
    })
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // ── Data ────────────────────────────────────────────────────────────────
  loadProducts(): void {
    this.loading.set(true);
    const obs = this.searchQuery().trim()
      ? this.svc.search(this.searchQuery(), this.page(), this.pageSize)
      : this.svc.getAll(this.page(), this.pageSize);

    obs.pipe(
      catchError(() => of(this.mockPage())),
      takeUntil(this.destroy$)
    ).subscribe(p => this.applyPage(p));
  }

  private applyPage(p: Page<Product>): void {
    this.products.set(p.content);
    this.totalElements.set(p.totalElements);
    this.totalPages.set(p.totalPages);
    this.loading.set(false);
  }

  // ── Actions ─────────────────────────────────────────────────────────────
  onSearch(query: string): void {
    this.searchQuery.set(query);
    this.search$.next(query);
  }

  setFilter(f: any): void { this.activeFilter.set(f); }

  sort(field: SortField): void {
    if (this.sortField() === field) this.sortDir.update(d => (d === 1 ? -1 : 1));
    else { this.sortField.set(field); this.sortDir.set(1); }
  }

  sortIcon(field: SortField): string {
    if (this.sortField() !== field) return '↕';
    return this.sortDir() === 1 ? '↑' : '↓';
  }

  changePage(p: number): void {
    if (p < 0 || p >= this.totalPages()) return;
    this.page.set(p);
    this.loadProducts();
  }

  cycleStatus(product: Product): void {
    const cycle: Record<ProductStatus, ProductStatus> = {
      DRAFT: 'PUBLISHED', PUBLISHED: 'ARCHIVED', ARCHIVED: 'DRAFT'
    };
    const next = cycle[product.status];
    this.svc.updateStatus(product.id, next).pipe(
      catchError(() => of(next)),
      takeUntil(this.destroy$)
    ).subscribe(() => {
      this.products.update(list =>
        list.map(p => p.id === product.id ? { ...p, status: next } : p)
      );
      this.toast(`Statut → ${next}`, 'success');
    });
  }

  openCreate(): void {
    this.editingProduct.set(null);
    this.form = this.emptyForm();
    this.showModal.set(true);
  }

  openEdit(product: Product): void {
    this.editingProduct.set(product);
    this.form = {
      name: product.name,
      price: product.price,
      availableQuantity: product.availableQuantity,
      description: product.description,
      status: product.status,
    };
    this.showModal.set(true);
  }

  closeModal(): void { this.showModal.set(false); }

  save(): void {
    if (!this.form.name?.trim() || this.form.price == null) {
      this.toast('Nom et prix sont requis', 'error');
      return;
    }
    const editing = this.editingProduct();
    if (editing) {
      // Update locally (status endpoint only in API)
      if (editing.status !== this.form.status) {
        this.cycleStatus(editing);
      }
      this.products.update(list =>
        list.map(p => p.id === editing.id ? { ...p, ...this.form } as Product : p)
      );
      this.toast('Produit modifié', 'success');
    } else {
      this.svc.create(this.form).pipe(
        catchError(() => {
          const mock: Product = {
            id: Date.now(), createdAt: new Date().toISOString().slice(0, 10),
            ...this.form, price: this.form.price!, tags: [], category: undefined
          };
          return of(mock);
        }),
        takeUntil(this.destroy$)
      ).subscribe(p => {
        this.products.update(list => [p, ...list]);
        this.totalElements.update(n => n + 1);
        this.toast('Produit créé', 'success');
      });
    }
    this.closeModal();
  }

  // ── UI Helpers ──────────────────────────────────────────────────────────
  qtyClass(qty: number): string {
    if (qty === 0) return 'out';
    if (qty < 10) return 'low';
    return 'ok';
  }

  statusCycle: Record<ProductStatus, ProductStatus> = {
    DRAFT: 'PUBLISHED', PUBLISHED: 'ARCHIVED', ARCHIVED: 'DRAFT'
  };

  formatPrice(price: number): string {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(price);
  }

  pageRange(): number[] {
    const total = this.totalPages();
    const cur = this.page();
    if (total <= 7) return this.pages();
    const start = Math.max(0, cur - 2);
    const end = Math.min(total - 1, cur + 2);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  trackById(_: number, p: Product) { return p.id; }

  skeletons = Array(6).fill(0);

  private emptyForm(): ProductForm {
    return { name: '', price: null, availableQuantity: 0, description: '', status: 'DRAFT' };
  }

  private toast(message: string, type: 'success' | 'error'): void {
    const id = ++this.toastCounter;
    this.toasts.update(t => [...t, { message, type, id }]);
    setTimeout(() => this.toasts.update(t => t.filter(x => x.id !== id)), 3000);
  }

  // ── Mock fallback ───────────────────────────────────────────────────────
  private mockPage(): Page<Product> {
    const statuses: ProductStatus[] = ['PUBLISHED', 'DRAFT', 'ARCHIVED'];
    const content: Product[] = Array.from({ length: 10 }, (_, i) => ({
      id: this.page() * 10 + i + 1,
      name: `Produit ${String(this.page() * 10 + i + 1).padStart(3, '0')}`,
      price: parseFloat((Math.random() * 9800 + 200).toFixed(2)),
      availableQuantity: Math.floor(Math.random() * 150),
      description: 'Article de démonstration — backend non connecté',
      status: statuses[Math.floor(Math.random() * 3)],
      createdAt: new Date(Date.now() - Math.random() * 1e10).toISOString().slice(0, 10),
    }));
    return { content, totalElements: 47, totalPages: 5, number: this.page(), size: 10 };
  }
}
