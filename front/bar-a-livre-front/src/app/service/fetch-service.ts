import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class FetchService {


  private http = inject(HttpClient);
  /*
  headers: {
          Authorization : 'Bearer ' + localStorage.getItem('token')
        },
   */

  public login(username: string,password: string ) {
    this.http
      .post<string>('http://localhost:8080/api/v1/signin', {
        body: {
          username: username,
          password: password
        }
      })
      .subscribe((token) => {
        return token;
      });
  }

  public signUp(username: string, password: string, lastName: string, firstName: string) {
    try {
      this.http.post<string>('http://localhost:8080/api/v1/auth/signup', {
        body:{
          email:username,
          password: password,
          nom: lastName,
          prenom: firstName
        }
      }).subscribe(retour => {
        return retour;
      })
    }
    catch (e : any) {
      return e.message()
    }
  }
}
