import {Component, inject} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatHint, MatInput, MatLabel} from '@angular/material/input';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {submit} from '@angular/forms/signals';
import {AuthService} from '../../core/services/auth-service';

@Component({
  selector: 'app-inscription',
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatHint
  ],
  templateUrl: './inscription.html'
})
export class Inscription {

  auth = inject(AuthService);

  private readonly fb = new FormBuilder();
  signupForm = this.fb.nonNullable.group({
    email: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.email]}),
    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.pattern(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/)]
    }),
    lastName: new FormControl('', {nonNullable: true, validators: [Validators.required]}),
    firstName: new FormControl('', {nonNullable: true, validators: [Validators.required]})
  });

  onSubmit(event: Event) {
    event.preventDefault();
    if (this.signupForm.valid) {
      console.log("formValid")
        this.auth.signUp(this.signupForm.get("email")?.getRawValue(), this.signupForm.get("password")?.getRawValue(), this.signupForm.get("lastName")?.getRawValue(), this.signupForm.get("firstName")?.getRawValue())
      }
    }
}
