import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {jwtDecode, JwtPayload} from "jwt-decode";

interface authToken extends JwtPayload {
  nom: string,
  prenom: string,
  email: string,
  role: string
}

export class AuthError {
  private message: string;

  constructor(message: string) {
    this.message = message;
  }
  getMessage() {
    return this.message
  }
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
    return this.http
      .post<HttpResponse<{token:string}>>('http://localhost:8080/api/v1/auth/signin', {
          "email": username,
          "password": password
        }
      )
      .subscribe((response) => {
        /*
        if(response.status != 200) {
          return new AuthError("Erreur serveur");
        }
        */
        const token = JSON.stringify(response);
        const o = JSON.parse(token);
        localStorage.setItem("token", o.token);
        return "OK";
      });
  }

  public signUp(username: string, password: string, lastName: string, firstName: string) {
    try {
      this.http.post<HttpResponse<string>>('http://localhost:8080/api/v1/auth/signup', {
          email:username,
          password: password,
          nom: lastName,
          prenom: firstName
      }).subscribe(response => {
        if(response.ok){
          this.login(username, password)
          return;
        }
        else {
          return new AuthError(response.body ?? "Erreur serveur")
        }

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
  public getUserId() {
    const token = localStorage.getItem("token") ?? "";
    const decodedToken = jwtDecode<authToken>(token);
    const userId = decodedToken.sub ?? "-1";
    return Number.parseInt(userId);
  }
}
