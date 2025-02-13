import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MusicService {

  private url  = environment;

  constructor(httpClient: HttpClient) { 
  }

  obterMusicas() {
    return 
  }
}
