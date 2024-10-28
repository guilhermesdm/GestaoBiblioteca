import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {EmprestimoService} from "../service/emprestimo-service";
import {ApiUtils} from "../../../util/ApiUtils";
import {debounceTime, switchMap} from "rxjs";
import {SearchAutoCompleteService} from "../autocomplete/search-auto-complete-service";
import {Usuario} from "../../../model/usuario";
import {Livro} from "../../../model/livro";
import {EmprestimoStatus, getNomeStatus} from "../../../model/emprestimoStatus";

@Component({
  selector: 'app-emprestimo-cadastro',
  templateUrl: './emprestimo-cadastro.component.html',
  styleUrls: ['./emprestimo-cadastro.component.scss']
})
export class EmprestimoCadastroComponent implements OnInit {

  statuses: EmprestimoStatus[] = Object.values(EmprestimoStatus);

  form: FormGroup;

  usuarios: Usuario[] = [];
  livros: Livro[] = [];

  constructor(private service: EmprestimoService, private dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute, private searchService: SearchAutoCompleteService) {
    this.form = this.formBuilder.group({
      id: [],
      version: [],
      usuario: new FormControl('', Validators.required),
      livro: new FormControl('', Validators.required),
      dataEmprestimo: new FormControl('', Validators.required),
      dataDevolucao: new FormControl('', Validators.required),
      status: new FormControl({value: '', disabled: true})
    });

    this.listenerSelect();

    if (!!this.route.snapshot.paramMap) {
      let id = Number(this.route.snapshot.paramMap.get("id"));
      if (!id || id === 0) {
        return;
      }

      this.service.find(id)
        .subscribe({
          next: (result) => {
            this.form.setValue(result);
            this.form.controls['usuario'].disable()
            this.form.controls['livro'].disable()
            this.form.controls['dataEmprestimo'].disable()
            this.form.controls['status'].enable()
          },
          error: err => {
            this.voltar();
            this.throwError(err.error.errors)
          }
        })
    }
  }

  listenerSelect(): void {
    this.form.controls['usuario'].valueChanges.pipe(
      debounceTime(500),
      switchMap(value => this.searchService.search(ApiUtils.USUARIO_URL + "/list-all", value))
    ).subscribe({
      next: value => {
        this.usuarios = value;
      },
      error: err => {
        this.throwError(err.error.errors)
      }
    });

    this.form.controls['livro'].valueChanges.pipe(
      debounceTime(500),
      switchMap(value => this.searchService.search(ApiUtils.LIVRO_URL + "/list-all", value))
    ).subscribe({
      next: value => {
        this.livros = value;
      },
      error: err => {
        this.throwError(err.error.errors)
      }
    });
  }

  ngOnInit() {

  }

  onSubmit() {
    if (this.form.valid) {
      let value = this.form.value;
      if (value.id == null) {
        this.service.persist(this.form.value);
      } else {
        this.service.atualizar(this.form.value);
      }
    }
  }

  voltar() {
    this.router.navigateByUrl('/emprestimo');
  }

  throwError(data: ErrorBean): void {
    let matDialogConfig = new MatDialogConfig();
    matDialogConfig.data = data;
    this.dialog.open(ErrorMessageComponent, matDialogConfig);
  }

  getOptionUsuario(option: Usuario): string {
    return option.nome
  }

  getOptionLivro(option: Livro): string {
    return option.titulo
  }

  protected readonly getNomeStatus = getNomeStatus;
  protected readonly status = status;
}
