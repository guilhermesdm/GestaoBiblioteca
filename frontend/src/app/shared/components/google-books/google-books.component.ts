import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ErrorBean} from "../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../error/component/error-message.component";
import {Livro} from "../../model/livro";
import {getNomeCategoria} from "../../model/LivroCategoria";
import {GoogleBooksService} from "./service/google-books-service";
import {PopupComponent} from "../../popup/popup.component";

@Component({
  selector: 'app-google-books',
  templateUrl: './google-books.component.html',
  styleUrls: ['./google-books.component.scss']
})
export class GoogleBooksComponent implements OnInit {

  livros: Observable<Livro[]> | undefined;
  columnsName = ['titulo', 'autor', 'isbn', 'dataPublicacao', 'categoria', 'actions'];

  constructor(private service: GoogleBooksService, private dialog: MatDialog) {
  }

  throwError(data: ErrorBean): void {
    let matDialogConfig = new MatDialogConfig();
    matDialogConfig.data = data;
    this.dialog.open(ErrorMessageComponent, matDialogConfig);
  }

  ngOnInit(): void {

  }

  protected readonly getNomeCategoria = getNomeCategoria;
  search: string | undefined;

  adicionar(livro: Livro): void {
    this.service.addLivro(livro)
      .subscribe({
        next: (res) => {
          let matDialogConfig = new MatDialogConfig();
          matDialogConfig.data = {
            titulo: res.message
          };
          this.dialog.open(PopupComponent, matDialogConfig);
        },
        error: err => {
          this.throwError(err.error.errors);
        }
      })
  }

  pesquisar() {
    this.livros = this.service.listaLivros(this.search)
  }
}
