import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LocationSearchService {
  private serverHost: string = environment.serverHost;

  constructor(public http: HttpClient,) {
  }

  search(keyword: any) {
    const params = {keyword};
    return this.http.get<any>(`${this.serverHost}/places/search`, {params})
  }
}
