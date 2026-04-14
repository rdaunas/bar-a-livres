import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AuthService {

  // Setup session obj in app-root with role + nom + prenom
  // Conditional rendering nav link by session.role
  // Auto login after signup

  private http = inject(HttpClient);

  public addToken() : HttpHeaders {
    return new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem('token')});
  }


  public login(username: string,password: string ) {
    const h  = new HttpHeaders({'Access-Control-Allow-Origin': '*'});
    this.http
      .post<string>('http://localhost:8080/api/v1/auth/signin', {
          "email": username,
          "password": password
        }
      )
      .subscribe((response) => {
        const token = JSON.stringify(response);
        const o = JSON.parse(token);
        localStorage.setItem("token", o.token);
      });
  }

  public signUp(username: string, password: string, lastName: string, firstName: string) {
    try {
      this.http.post<string>('http://localhost:8080/api/v1/auth/signup', {
          email:username,
          password: password,
          nom: lastName,
          prenom: firstName
      }).subscribe(retour => {
        return retour;
      })
    }
    catch (e : any) {
      return e.message()
    }
  }

  public logout() {
    localStorage.removeItem("token");
    //navigate to home
  }
}
