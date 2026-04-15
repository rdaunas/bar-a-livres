import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {jwtDecode, JwtPayload} from "jwt-decode";

interface authToken extends JwtPayload {
  nom: string,
  prenom: string,
  email: string,
  role: string
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {


  private http = inject(HttpClient);

  public addToken() : HttpHeaders {
    return new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem('token')});
  }


  public login(username: string,password: string ) {
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
      }).subscribe(response => {
        this.login(username, password)
      })
    }
    catch (e : any) {
      return e.message()
    }
  }

  public logout() {
    localStorage.removeItem("token");
  }

  public isAuthenticated() {
    return localStorage.getItem("token") != null || localStorage.getItem("token") != undefined;

  }

  public hasRole(roleName: string[]) {
    const token = localStorage.getItem("token") ?? "";
    const decodedToken = jwtDecode<authToken>(token);
    roleName.forEach(name => {
      return name == decodedToken.role;
    })
  }
}
