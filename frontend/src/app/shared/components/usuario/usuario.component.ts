import {Component, OnInit} from '@angular/core';
import {UsuarioService} from "./service/usuario.service";
import {Usuario} from "../../model/usuario";
import {catchError, Observable, of} from "rxjs";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ErrorBean} from "../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../error/component/error-message.component";
import {Router} from "@angular/router";
import {PopupComponent} from "../../popup/popup.component";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  usuarios: Observable<Usuario[]> | undefined;
  columnsName = ['nome', 'email', 'dataCadastro', 'telefone', 'actions'];

  constructor(private service: UsuarioService, private dialog: MatDialog, private router: Router) {
  }

  cadastrar(): void {
    this.router.navigateByUrl('/usuario/cadastro');
  }

  loadUsuarios(): void {
    this.usuarios = this.service.listaUsuarios()
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
    this.loadUsuarios();
  }

  edit(usuario: Usuario): void {
    this.router.navigateByUrl(`/usuario/cadastro/${usuario.id}`);
  }

  recomendar(usuario: Usuario): void {
    this.service.recomendar(usuario.id).subscribe({
      next: result => {
        let config = new MatDialogConfig();

        if (result.body !== null && result.body !== undefined) {
          config.data = {
            titulo: result.message,
            mensagem: result.body,
          };
        } else {
          config.data = {
            titulo: "Nada a recomendar",
          }
        }

        this.dialog.open(PopupComponent, config)
      },
      error: err => {
        this.throwError(err.error.errors)
      }
    })
  }

  delete(usuario: Usuario): void {
    this.service.delete(usuario.id).subscribe({
      next: () => {
        this.loadUsuarios();
      },
      error: err => {
        this.throwError(err.error.errors)
      }
    });
  }

}
