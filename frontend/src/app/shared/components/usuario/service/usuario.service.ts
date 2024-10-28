import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Usuario} from "../../../model/usuario";
import {map, Observable} from "rxjs";
import {ResponseBody} from "../../../model/responseBody";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {ResponseBodyBean} from "../../../model/responseBodyBean";
import {ApiUtils} from "../../../util/ApiUtils";
import {Livro} from "../../../model/livro";
import {PopupComponent} from "../../../popup/popup.component";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private readonly ENDPOINT = ApiUtils.USUARIO_URL;

  constructor(private http: HttpClient, private dialog: MatDialog, private router: Router) {
  }

  listaUsuarios(): Observable<Usuario[]> {
    return this.http.get<ResponseBody<Usuario>>(this.ENDPOINT + '/list-all')
      .pipe(map(res => res.body));
  }

  persist(value: Livro): void {
    let observable: Observable<ResponseBodyBean<any>>;

    if (!value.id) {
      observable = this.http.post<ResponseBodyBean<any>>(this.ENDPOINT, value);
    } else {
      observable = this.http.put<ResponseBodyBean<any>>(this.ENDPOINT + `/${value.id}`, value);
    }

    observable.subscribe({
      next: async result => {
        let booleanPromise = await this.router.navigateByUrl('/usuario');
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

  recomendar(value: number) {
    return this.http.get<ResponseBody<Livro>>(this.ENDPOINT + `/recomendar/${value}`)
  }

  delete(id: number): Observable<Object> {
    return this.http.delete(this.ENDPOINT + "/" + id);
  }

  find(id: number): Observable<Usuario> {
    return this.http.get<ResponseBodyBean<Usuario>>(this.ENDPOINT + '/' + id).pipe(map(res => res.body));
  }

  throwError(errors: ErrorBean[]): void {
    let config = new MatDialogConfig();
    config.data = errors;
    this.dialog.open(ErrorMessageComponent, config)
  }

}
