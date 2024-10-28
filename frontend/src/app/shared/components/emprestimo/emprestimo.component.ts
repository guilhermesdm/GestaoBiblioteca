import {Component, OnInit} from '@angular/core';
import {catchError, Observable, of} from "rxjs";
import {Emprestimo, EmprestimoList} from "../../model/emprestimo";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {ErrorBean} from "../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../error/component/error-message.component";
import {EmprestimoService} from "./service/emprestimo-service";

@Component({
  selector: 'app-emprestimo',
  templateUrl: './emprestimo.component.html',
  styleUrls: ['./emprestimo.component.scss']
})
export class EmprestimoComponent implements OnInit {

  emprestimos: Observable<EmprestimoList[]> | undefined;
  columnsName = ['usuario', 'livro', 'dataEmprestimo', 'dataDevolucao', 'actions'];

  constructor(private service: EmprestimoService, private dialog: MatDialog, private router: Router) {
  }

  cadastrar(): void {
    this.router.navigateByUrl('/emprestimo/cadastro');
  }

  loadEmprestimos(): void {
    this.emprestimos = this.service.listaEmprestimos()
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
    this.loadEmprestimos();
  }

  edit(emprestimo: Emprestimo): void {
    this.router.navigateByUrl(`/emprestimo/cadastro/${emprestimo.id}`);
  }
}
