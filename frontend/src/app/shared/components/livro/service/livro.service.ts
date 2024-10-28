import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {ResponseBody} from "../../../model/responseBody";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {ResponseBodyBean} from "../../../model/responseBodyBean";
import {Livro, LivroInfo} from "../../../model/livro";
import {ApiUtils} from "../../../util/ApiUtils";
import {PopupComponent} from "../../../popup/popup.component";

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  private readonly ENDPOINT = ApiUtils.LIVRO_URL;

  constructor(private http: HttpClient, private dialog: MatDialog, private router: Router) {
  }

  listaLivros(): Observable<Livro[]> {
    return this.http.get<ResponseBody<Livro>>(this.ENDPOINT + '/list-all')
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
        let booleanPromise = await this.router.navigateByUrl('/livro');
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

  delete(id: number): Observable<Object> {
    return this.http.delete(this.ENDPOINT + "/" + id);
  }

  find(id: number): Observable<Livro> {
    return this.http.get<ResponseBodyBean<Livro>>(this.ENDPOINT + '/' + id)
      .pipe(map(res => res.body));
  }

  buscarInfoLivro(id: string): Observable<LivroInfo> {
    return this.http.get<ResponseBodyBean<LivroInfo>>(this.ENDPOINT + '/info/' + id)
      .pipe(map(res => res.body));
  }

  throwError(errors: ErrorBean[]): void {
    let config = new MatDialogConfig();
    config.data = errors;
    this.dialog.open(ErrorMessageComponent, config)
  }

}
