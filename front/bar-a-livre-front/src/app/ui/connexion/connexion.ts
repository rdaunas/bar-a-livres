import {Component, inject, signal} from '@angular/core';

import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../core/service/auth-service';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {Router} from '@angular/router';

@Component({
  selector: 'app-connexion',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatButton,
    MatInput
  ],
  templateUrl: './connexion.html'
})
export class Connexion {

  auth = inject(AuthService);


  private readonly fb = new FormBuilder();

  loginForm = this.fb.group({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  })

  onSubmit(event: Event) {
    event.preventDefault();
    if (this.loginForm.get('email') != null && this.loginForm.get("password") != null) {
      this.auth.login(this.loginForm.get("email")?.getRawValue(), this.loginForm.get("password")?.getRawValue())
    }
  }

}
