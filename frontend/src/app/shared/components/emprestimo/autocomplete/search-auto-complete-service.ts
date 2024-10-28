import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ResponseBody} from "../../../model/responseBody";
import {map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SearchAutoCompleteService {
  constructor(private http: HttpClient) {}

  search(endpoint: string, query: string) {
    return this.http.get<ResponseBody<any>>(`${endpoint}?query=${encodeURIComponent(query)}`)
      .pipe(map(res => res.body));
  }
}
