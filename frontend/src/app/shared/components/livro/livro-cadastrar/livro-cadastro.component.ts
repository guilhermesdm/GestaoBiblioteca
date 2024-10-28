import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {LivroService} from "../service/livro.service";
import {getNomeCategoria, LivroCategoria} from "../../../model/LivroCategoria";

@Component({
  selector: 'app-livro-cadastrar',
  templateUrl: './livro-cadastro.component.html',
  styleUrls: ['./livro-cadastro.component.scss']
})
export class LivroCadastroComponent implements OnInit {
  form: FormGroup;

  categorias = Object.values(LivroCategoria);

  constructor(private service: LivroService, private dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.form = this.formBuilder.group({
      id: [],
      version: [],
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      isbn: ['', Validators.required],
      dataPublicacao: ['', Validators.required],
      categoria: ['', Validators.required],
      googleBooksId: []
    })
  }

  ngOnInit() {
    if (!!this.route.snapshot.paramMap) {
      let id = Number(this.route.snapshot.paramMap.get("id"));
      if (!id || id === 0) {
        return;
      }
      this.service.find(id)
        .subscribe({
          next: (result) => {
            this.form.setValue(result);
          },
          error: err => {
            this.voltar();
            this.throwError(err.error.errors)
          }
        })
    }
  }

  onSubmit() {
    if (this.form.valid) {
      this.service.persist((this.form.value))
    }
  }

  voltar() {
    this.router.navigateByUrl('/livro');
  }

  throwError(data: ErrorBean): void {
    let matDialogConfig = new MatDialogConfig();
    matDialogConfig.data = data;
    this.dialog.open(ErrorMessageComponent, matDialogConfig);
  }

  protected readonly getNomeCategoria = getNomeCategoria;
}
