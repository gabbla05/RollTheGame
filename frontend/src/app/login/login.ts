import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Api } from '../api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule], // Potrzebne do formularzy
  templateUrl: './login.html'
})
export class Login {
  user = { username: '', password: '' };

  constructor(private api: Api) {}

  onSubmit() {
    this.api.login(this.user);
  }
}
