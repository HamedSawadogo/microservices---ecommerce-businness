import { Routes } from '@angular/router';
import {ProductListComponent} from './components/products-list/products-list';
import {ProductFormComponent} from './components/product-form/product-form';
import {OrderListComponent} from './components/orders/orders';
import {PaymentFormComponent} from './components/payment-form/payment-form';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'products',
    pathMatch: 'full'
  },
  {
    path: 'products',
    component: ProductListComponent,
    title: 'Catalogue Produits'
  },
  {
    path: 'products/new',
    component: ProductFormComponent,
    title: 'Nouveau Produit'
  },
  {
    path: 'orders',
    component: OrderListComponent,
    title: 'Commandes'
  },
  {
    path: 'payment',
    component: PaymentFormComponent,
    title: 'Paiement'
  },
  {
    path: '**',
    redirectTo: 'products'
  }
];
