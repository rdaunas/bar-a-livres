import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {jwtDecode, JwtPayload} from "jwt-decode";
import {Router} from '@angular/router';
import {UserRole} from '../auth/user-role.model';

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
  private router = inject(Router);

  public addToken() : HttpHeaders {
    return new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem('token')});
  }


  public login(username: string,password: string ) {
    return this.http
      .post<HttpResponse<{token:string}>>('http://localhost:8080/api/v1/auth/signin', {
          "email": username,
          "password": password
        },{ observe: 'response' }
      )
      .subscribe((response) => {
        if(response.status != 200) {
          return new AuthError("Erreur serveur");
        }
        const token = JSON.stringify(response.body);
        const o = JSON.parse(token);
        localStorage.setItem("token", o.token);
        this.router.navigate(['/catalogue']);
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
      },{ observe: 'response' }).subscribe(response => {
        if(response.status == 200){
          this.login(username, password);
          return;
        }
        else {
          return new AuthError(response.body?.body ?? "Erreur serveur")
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

  public hasRole(roleName: string[]) : boolean {
    const token = localStorage.getItem("token") ?? "";
    const decodedToken = jwtDecode<authToken>(token);
    return roleName.some(name => {
      return name.toLowerCase() == decodedToken.role.toLowerCase();
    })
  }

  public role() {
    const token = localStorage.getItem("token") ?? "";
    const decodedToken = jwtDecode<authToken>(token);
    return decodedToken.role.toLowerCase() as UserRole;
  }
}
