import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


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
    fetch('http://localhost:8080/api/v1/signin', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }).then(res => res.json()).then(data => {
      localStorage.setItem('token', data.accessToken);
    })
  }

  public signUp(username: string, password: string, lastName: string, firstName: string) {
    try {
      fetch('http://localhost:8080/api/v1/auth/signup', {
        method: 'POST',
        body: JSON.stringify({
          email:username,
          password: password,
          nom: lastName,
          prenom: firstName
        })
      }).then(res => res.json()).then(data => {
        return data.toString();
      })
    }
    catch (e : any) {
      return e.message()
    }
  }
}
