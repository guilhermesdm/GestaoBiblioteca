import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {map, Observable} from "rxjs";
import {Emprestimo, EmprestimoList, EmprestimoRetorno} from "../../../model/emprestimo";
import {ResponseBody} from "../../../model/responseBody";
import {ResponseBodyBean} from "../../../model/responseBodyBean";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {ApiUtils} from "../../../util/ApiUtils";
import {PopupComponent} from "../../../popup/popup.component";

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  private readonly ENDPOINT = ApiUtils.EMPRESTIMO_URL;

  constructor(private http: HttpClient, private dialog: MatDialog, private router: Router) {
  }

  listaEmprestimos(): Observable<EmprestimoList[]> {
    return this.http.get<ResponseBody<EmprestimoList>>(this.ENDPOINT + '/list-all')
      .pipe(map(res => res.body));
  }

  persist(value: Emprestimo): void {
    this.http.post<ResponseBodyBean<any>>(this.ENDPOINT, value)
      .subscribe({
        next: async result => {
          let booleanPromise = await this.router.navigateByUrl('/emprestimo');
          if (booleanPromise) {
            let config = new MatDialogConfig();
            config.data = {
              titulo: "Sucesso",
              mensagem: result.message,
            };
            this.dialog.open(PopupComponent, config)
          }
        },
        error: err => {
          this.throwError(err.error.errors)
        }
      });
  }

  atualizar(value: EmprestimoRetorno): void {
    this.http.post<ResponseBodyBean<any>>(this.ENDPOINT + '/atualizar', value)
      .subscribe({
        next: async result => {
          let booleanPromise = await this.router.navigateByUrl('/emprestimo');
          if (booleanPromise) {
            let config = new MatDialogConfig();
            config.data = {
              titulo: "Sucesso",
              mensagem: result.message,
            };
            this.dialog.open(PopupComponent, config)
          }
        },
        error: err => {
          this.throwError(err.error.errors)
        }
      });
  }

  find(id: number): Observable<EmprestimoRetorno> {
    return this.http.get<ResponseBodyBean<EmprestimoRetorno>>(this.ENDPOINT + '/' + id).pipe(map(res => res.body));
  }

  throwError(errors: ErrorBean[]): void {
    let config = new MatDialogConfig();
    config.data = errors;
    this.dialog.open(ErrorMessageComponent, config)
  }

}
