import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ProductsComponent} from './product-list/product-list';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProductsComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ecomm-front-end');
}
