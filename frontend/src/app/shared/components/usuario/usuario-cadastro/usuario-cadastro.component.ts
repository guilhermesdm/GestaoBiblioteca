import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UsuarioService} from "../service/usuario.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.scss']
})
export class UsuarioCadastroComponent implements OnInit {
  form: FormGroup;

  constructor(private service: UsuarioService, private dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.form = this.formBuilder.group({
      id: [],
      version: [],
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      dataCadastro: ['', Validators.required],
      telefone: ['', Validators.required]
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
    this.router.navigate(['/usuario']);
  }

  throwError(data: ErrorBean): void {
    let matDialogConfig = new MatDialogConfig();
    matDialogConfig.data = data;
    this.dialog.open(ErrorMessageComponent, matDialogConfig);
  }

}
