import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {ResponseBody} from "../../../model/responseBody";
import {ErrorMessageComponent} from "../../../error/component/error-message.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ErrorBean} from "../../../error/model/ErrorBean";
import {Livro} from "../../../model/livro";
import {ApiUtils} from "../../../util/ApiUtils";
import {ResponseBodyBean} from "../../../model/responseBodyBean";

@Injectable({
  providedIn: 'root'
})
export class GoogleBooksService {

  private readonly ENDPOINT = ApiUtils.GOOGLE_BOOKS_URL;

  constructor(private http: HttpClient, private dialog: MatDialog) {
  }

  listaLivros(titulo: string | undefined): Observable<Livro[]> {
    return this.http.get<ResponseBody<Livro>>(this.ENDPOINT + `?title=${titulo}`)
      .pipe(map(res => res.body));
  }

  addLivro(livro: Livro): Observable<ResponseBodyBean<any>> {
    return this.http.post<ResponseBodyBean<any>>(this.ENDPOINT, livro)
  }

  throwError(errors: ErrorBean[]): void {
    let config = new MatDialogConfig();
    config.data = errors;
    this.dialog.open(ErrorMessageComponent, config)
  }

}
