import {Component, OnInit} from '@angular/core';
import {catchError, Observable, of} from "rxjs";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {ErrorBean} from "../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../error/component/error-message.component";
import {Livro, LivroInfo} from "../../model/livro";
import {LivroService} from "./service/livro.service";
import {getNomeCategoria} from "../../model/LivroCategoria";
import {PopupComponent} from "../../popup/popup.component";

@Component({
  selector: 'app-livro',
  templateUrl: './livro.component.html',
  styleUrls: ['./livro.component.scss']
})
export class LivroComponent implements OnInit {

  livros: Observable<Livro[]> | undefined;
  columnsName = ['titulo', 'autor', 'isbn', 'dataPublicacao', 'categoria', 'actions'];

  constructor(private service: LivroService, private dialog: MatDialog, private router: Router) {
  }

  cadastrar(): void {
    this.router.navigateByUrl('/livro/cadastro');
  }

  loadLivros(): void {
    this.livros = this.service.listaLivros()
      .pipe(catchError(e => {
        this.throwError(e);
        return of([]);
      }));
  }

  throwError(data: ErrorBean): void {
    let matDialogConfig = new MatDialogConfig();
    matDialogConfig.data = data;
    this.dialog.open(ErrorMessageComponent, matDialogConfig);
  }

  ngOnInit(): void {
    this.loadLivros();
  }

  edit(livro: Livro): void {
    this.router.navigateByUrl(`/livro/cadastro/${livro.id}`);
  }

  delete(Livro: Livro): void {
    this.service.delete(Livro.id).subscribe({
      next: () => {
        this.loadLivros();
      },
      error: err => {
        this.throwError(err.error.errors)
      }
    });
  }

  showInfo(livro: Livro): void {
    if (!!livro.googleBooksId) {
      this.service.buscarInfoLivro(livro.googleBooksId)
        .subscribe({
          next: (result: LivroInfo) => {
            let config = new MatDialogConfig();
            config.data = {
              titulo: "Informações",
              mensagem: result.description
            }
            this.dialog.open(PopupComponent, config)
          },
          error: err => {
            this.throwError(err.error.errors)
          }
        })
    }
  }

  protected readonly getNomeCategoria = getNomeCategoria;
}
