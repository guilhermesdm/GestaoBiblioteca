import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatToolbarModule} from "@angular/material/toolbar";
import {HttpClientJsonpModule, HttpClientModule} from "@angular/common/http";
import {RouterLink, RouterOutlet} from "@angular/router";
import {ErrorMessageComponent} from './shared/error/component/error-message.component';
import {UsuarioComponent} from "./shared/components/usuario/usuario.component";
import {AppRoutingModule} from "./app-routing-module";
import {AppMaterialModule} from "./shared/app-material/app-material.module";
import {UsuarioCadastroComponent} from "./shared/components/usuario/usuario-cadastro/usuario-cadastro.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxMaskDirective, provideNgxMask} from "ngx-mask";
import {DatePipe} from "@angular/common";
import {LivroComponent} from './shared/components/livro/livro.component';
import {LivroCadastroComponent} from './shared/components/livro/livro-cadastrar/livro-cadastro.component';
import {EmprestimoComponent} from './shared/components/emprestimo/emprestimo.component';
import {
  EmprestimoCadastroComponent
} from './shared/components/emprestimo/emprestimo-cadastro/emprestimo-cadastro.component';
import {PopupComponent} from './shared/popup/popup.component';
import { GoogleBooksComponent } from './shared/components/google-books/google-books.component';

@NgModule({
  declarations: [
    AppComponent,
    UsuarioComponent,
    UsuarioCadastroComponent,
    ErrorMessageComponent,
    LivroComponent,
    LivroCadastroComponent,
    EmprestimoComponent,
    EmprestimoCadastroComponent,
    PopupComponent,
    GoogleBooksComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    HttpClientModule,
    RouterOutlet,
    RouterLink,
    AppMaterialModule,
    ReactiveFormsModule,
    HttpClientJsonpModule,
    NgxMaskDirective,
    DatePipe,
    FormsModule
  ],
  providers: [
    provideNgxMask()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
