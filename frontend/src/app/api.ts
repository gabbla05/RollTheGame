import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class Api {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private router: Router) {}

  // 1. Logowanie
  login(user: any) {
    this.http.post<any>(`${this.baseUrl}/auth/login`, user).subscribe({
      next: (res) => {
        // Sprawdzamy, czy jesteśmy w przeglądarce
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem('token', res.token);
        }
        this.router.navigate(['/games']);
      },
      error: () => alert('Błędne hasło lub login!')
    });
  }

  // 2. Pobieranie tokenu z pamięci przeglądarki (BEZPIECZNIE)
  private getHeaders() {
    let token = '';
    // Sprawdzamy, czy jesteśmy w przeglądarce przed użyciem localStorage
    if (typeof localStorage !== 'undefined') {
      token = localStorage.getItem('token') || '';
    }
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // 3. Pobieranie gier
  getGames() {
    return this.http.get<any[]>(`${this.baseUrl}/games`, { headers: this.getHeaders() });
  }

  // 4. Usuwanie gry
  deleteGame(id: number) {
    return this.http.delete(`${this.baseUrl}/games/${id}`, { headers: this.getHeaders() });
  }
}
