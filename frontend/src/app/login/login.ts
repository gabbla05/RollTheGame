import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Api } from '../api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  user = { username: '', password: '' };
  isLoginMode = true; // Domyślnie pokazujemy logowanie

  constructor(private api: Api) {}

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.user = { username: '', password: '' }; // Czyścimy pola przy przełączaniu
  }

  onSubmit() {
    if (this.isLoginMode) {
      this.api.login(this.user);
    } else {
      this.api.register(this.user).subscribe({
        next: () => {
          alert('Konto założone! Możesz się teraz zalogować.');
          this.isLoginMode = true; // Po sukcesie wracamy do ekranu logowania
        },
        error: () => alert('Błąd rejestracji. Użytkownik może już istnieć.')
      });
    }
  }
}
