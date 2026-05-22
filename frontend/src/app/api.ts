import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class Api {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private router: Router) {}

  login(user: any) {
    this.http.post<any>(`${this.baseUrl}/auth/login`, user).subscribe({
      next: (res) => {
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem('token', res.token);
        }
        this.router.navigate(['/games']);
      },
      error: () => alert('Błędne hasło lub login!')
    });
  }

  // NOWA METODA: Rejestracja
  register(user: any) {
    return this.http.post(`${this.baseUrl}/auth/register`, user, { responseType: 'text' });
  }

  logout() {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem('token'); // Usuwamy token
    }
    this.router.navigate(['/login']); // Wyrzucamy do logowania
  }

  // Funkcja wyciągająca rolę z tokena JWT
  isAdmin(): boolean {
    if (typeof localStorage !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          // JWT składa się z 3 części. Środkowa to dane (payload) w Base64
          const payload = JSON.parse(atob(token.split('.')[1]));
          const roles = payload.role || [];
          return roles.some((r: any) => r.authority === 'ROLE_ADMIN');
        } catch (e) {
          return false;
        }
      }
    }
    return false;
  }

  private getHeaders() {
    let token = '';
    if (typeof localStorage !== 'undefined') {
      token = localStorage.getItem('token') || '';
    }
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getGames() {
    return this.http.get<any[]>(`${this.baseUrl}/games`, { headers: this.getHeaders() });
  }

  getRandomGame() {
    return this.http.get<any>(`${this.baseUrl}/games/find/random`, { headers: this.getHeaders() });
  }

  deleteGame(id: number) {
    return this.http.delete(`${this.baseUrl}/games/${id}`, { headers: this.getHeaders() });
  }

  // --- NOWE METODY ---

  // Dodawanie nowej gry (dla Usera)
  addGame(game: any) {
    return this.http.post<any>(`${this.baseUrl}/games`, game, { headers: this.getHeaders() });
  }

  // Pobieranie użytkowników (dla Admina)
  getUsers() {
    return this.http.get<any[]>(`${this.baseUrl}/users`, { headers: this.getHeaders() });
  }

  // Usuwanie użytkowników (dla Admina)
  deleteUser(id: number) {
    return this.http.delete(`${this.baseUrl}/users/${id}`, { headers: this.getHeaders() });
  }
}
