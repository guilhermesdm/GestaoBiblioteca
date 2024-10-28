import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {UsuarioComponent} from "./shared/components/usuario/usuario.component";
import {UsuarioCadastroComponent} from "./shared/components/usuario/usuario-cadastro/usuario-cadastro.component";
import {LivroComponent} from "./shared/components/livro/livro.component";
import {LivroCadastroComponent} from "./shared/components/livro/livro-cadastrar/livro-cadastro.component";
import {EmprestimoComponent} from "./shared/components/emprestimo/emprestimo.component";
import {
  EmprestimoCadastroComponent
} from "./shared/components/emprestimo/emprestimo-cadastro/emprestimo-cadastro.component";
import {GoogleBooksComponent} from "./shared/components/google-books/google-books.component";

export const routes: Routes = [
  {path: '', redirectTo: "usuario", pathMatch: 'full'},

  {path: 'usuario', component: UsuarioComponent},
  {path: 'usuario/cadastro', component: UsuarioCadastroComponent},
  {path: 'usuario/cadastro/:id', component: UsuarioCadastroComponent},

  {path: 'livro', component: LivroComponent},
  {path: 'livro/cadastro', component: LivroCadastroComponent},
  {path: 'livro/cadastro/:id', component: LivroCadastroComponent},

  {path: 'emprestimo', component: EmprestimoComponent},
  {path: 'emprestimo/cadastro', component: EmprestimoCadastroComponent},
  {path: 'emprestimo/cadastro/:id', component: EmprestimoCadastroComponent},

  {path: 'google-books', component: GoogleBooksComponent},

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
